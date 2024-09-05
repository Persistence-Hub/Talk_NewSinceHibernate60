package com.thorben.janssen;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.criteria.CriteriaDefinition;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thorben.janssen.dao.ChessGameDao_;
import com.thorben.janssen.model.ChessGame;
import com.thorben.janssen.model.ChessGame_;
import com.thorben.janssen.model.ChessMove;
import com.thorben.janssen.model.Move;
import com.thorben.janssen.model.MoveColor;
import com.thorben.janssen.model.MoveId;
import com.thorben.janssen.multitenancy.TenantIdResolver;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.Root;

public class TestHibernate {

	Logger log = LogManager.getLogger(this.getClass().getName());

	private EntityManagerFactory emf;

	private Long gameId;

	@Before
	public void init() {
		emf = Persistence.createEntityManagerFactory("my-persistence-unit");
		TimeZone.setDefault(TimeZone.getTimeZone("CET"));

		// manually set tenant id -- !!! DON'T DO THAT AT HOME !!!
		// ((TenantIdResolver) ((SessionFactoryImplementor) emf.unwrap(SessionFactory.class)).getCurrentTenantIdentifierResolver()).setTenantIdentifier("tenant1");

		gameId = prepareTestData();
	}

	@After
	public void close() {
		emf.close();
	}

	/**
	 * Improved timezone handling
	 */
	@Test
	public void timezoneStorageType() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessGame game = new ChessGame();
		game.setDate(ZonedDateTime.now());
		game.setPlayerWhite("Thorben Janssen");
		game.setPlayerBlack("Another player");
		game.setRound(1);
		em.persist(game);
		
		em.getTransaction().commit();
		em.close();

		em = emf.createEntityManager();
		em.getTransaction().begin();

		game = em.find(ChessGame.class, game.getId());
		log.info(game.getDate());
		
		em.getTransaction().commit();
		em.close();
	}


	/**
	 * Improved Criteria API
	 */
	@Test
	public void criteriaFromHql() {
		log.info("... criteriaFromHql ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		HibernateCriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
		JpaCriteriaQuery<ChessGame> criteriaQuery = builder.createQuery("SELECT g FROM ChessGame g", ChessGame.class);
		Root<?> gameRoot = criteriaQuery.getRootList().get(0);
		
		criteriaQuery.where(builder.like(gameRoot.get(ChessGame_.PLAYER_WHITE), "Thorben %"));
		ChessGame game = em.createQuery(criteriaQuery).getSingleResult();
		
		assertThat(game).isNotNull();
		
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void criteriaDefinition() {
		log.info("... criteriaDefinition ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		ChessGame game = new CriteriaDefinition<>(em, ChessGame.class) {{
										var game = from(ChessGame.class);
										where(like(game.get(ChessGame_.PLAYER_WHITE), "Thorben %"));
									}}
									.createQuery(em)
									.getSingleResult();

		assertThat(game).isNotNull();

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void criteriaDefinitionFromHql() {
		log.info("... criteriaDefinitionFromHql ...");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessGame game = new CriteriaDefinition<>(em, ChessGame.class, "SELECT g FROM ChessGame g") {{
										where(
												like(getRootList().get(0).get(ChessGame_.PLAYER_WHITE), 
												     "Thorben %")
										);
									}}
									.createQuery(em)
									.getSingleResult();

		assertThat(game).isNotNull();

		em.getTransaction().commit();
		em.close();
	}


	/**
	 * Records
	 */
	@Test
	public void embeddable() {
		log.info("... embeddable ...");

		// Find game
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessMove move = new ChessMove();
		// move.setId(new MoveId(UUID.randomUUID()));
		move.setId(UUID.randomUUID());
		move.setMoveNumber(1);
		move.setMove(new Move(MoveColor.WHITE, "e4"));
		em.persist(move);
		
		em.createQuery("SELECT m FROM ChessMove m WHERE m.move.move = :move")
		  .setParameter("move", "e4")
		  .getResultList();

		em.getTransaction().commit();
		em.close();
	}


	/**
	 * Soft Delete
	 */
	@Test
	public void softDelete() {
		log.info("... softDelete ...");

		// Find game
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessGame game = em.find(ChessGame.class, gameId);
		log.info("Found a game played between "+game.getPlayerWhite()+" and "+game.getPlayerBlack());
		
		em.getTransaction().commit();
		em.close();


		// Soft delete game
		em = emf.createEntityManager();
		em.getTransaction().begin();

		game = em.find(ChessGame.class, gameId);
		em.remove(game);
		log.info("Delete the game between "+game.getPlayerWhite()+" and "+game.getPlayerBlack());
		
		em.getTransaction().commit();
		em.close();


		// Find soft deleted game
		em = emf.createEntityManager();
		em.getTransaction().begin();

		game = em.find(ChessGame.class, gameId);
		assertThat(game).isNull();
		log.info("Couldn't find the game by its id");
		
		List<ChessGame> games = em.createQuery("SELECT g FROM ChessGame g WHERE g.playerWhite = :playerWhite", ChessGame.class)
								  .setParameter("playerWhite", "Thorben Janssen")
								  .getResultList();
		assertThat(games).isEmpty();
		log.info("Couldn't find the game via query");

		em.getTransaction().commit();
		em.close();
	}


	/**
	 * Generated DAOs
	 */
	@Test
	public void namedQuery() {
		log.info("... namedQuery ...");

		// Find game
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<ChessGame> games = ChessGame_.findGamesOfPlayer(em, "Thorben Janssen");
		games.forEach(g -> log.info(g.getPlayerWhite() + " - " + g.getPlayerBlack() + " played on " + g.getDate()));
		
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void daoCustomerQuery() {
		log.info("... daoCustomerQuery ...");

		// Find game
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<ChessGame> games = ChessGameDao_.findGamesByPlayer(em, "Thorben Janssen");
		games.forEach(g -> log.info(g.getPlayerWhite() + " - " + g.getPlayerBlack() + " played on " + g.getDate()));
		
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void daoGeneratedQuery() {
		log.info("... daoGeneratedQuery ...");

		// Find game
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		List<ChessGame> games = ChessGameDao_.generatedFind(em, "Thorben Janssen");
		games.forEach(g -> log.info(g.getPlayerWhite() + " - " + g.getPlayerBlack() + " played on " + g.getDate()));
		
		em.getTransaction().commit();
		em.close();
	}










	/**
	 * Bonus:
	 * Improved multi tenancy
	 */
	@Test
	public void multiTenancy() {
		log.info("... multiTenancy ...");

		// Persist game
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessGame game = new ChessGame();
		game.setDate(ZonedDateTime.now());
		game.setPlayerWhite("Thorben Janssen");
		game.setPlayerBlack("Someone on the internet");
		em.persist(game);
		
		em.getTransaction().commit();
		em.close();

		// Find game
		em = emf.createEntityManager();
		em.getTransaction().begin();

		List<ChessGame> games = em.createQuery("SELECT g FROM ChessGame g WHERE g.playerWhite = :player", ChessGame.class)
								  .setParameter("player", "Thorben Janssen")
								  .getResultList();
		
		em.getTransaction().commit();
		em.close();
	}

	private Long prepareTestData() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		ChessGame game = new ChessGame();
		game.setDate(ZonedDateTime.now());
		game.setPlayerWhite("Thorben Janssen");
		game.setPlayerBlack("Another player");
		game.setRound(1);
		em.persist(game);
		
		em.getTransaction().commit();
		em.close();

		return game.getId();
	}
}
