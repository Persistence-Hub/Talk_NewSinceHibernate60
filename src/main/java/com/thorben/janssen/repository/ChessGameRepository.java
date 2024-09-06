package com.thorben.janssen.repository;

import java.util.List;

import org.hibernate.annotations.processing.Find;

import com.thorben.janssen.model.ChessGame;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Insert;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import jakarta.data.repository.Update;

@Repository
public interface ChessGameRepository {
    
    @Insert
    void insert(ChessGame game);

    @Update
    void update(ChessGame game);

    @Update
    void updateAll(List<ChessGame> game);

    @Query("SELECT g FROM ChessGame g WHERE g.playerWhite = :player OR g.playerBlack = :player")
    // @HQL("SELECT g FROM ChessGame g WHERE g.playerWhite = :player OR g.playerBlack = :player")
    // @SQL("SELECT * FROM ChessGame g WHERE g.playerWhite = :player OR g.playerBlack = :player")
    List<ChessGame> findGamesByPlayer(String player);

    @Find
    List<ChessGame> generatedFind(String playerWhite);

}
