package com.thorben.janssen.dao;

import java.util.List;

import org.hibernate.annotations.processing.Find;
import org.hibernate.annotations.processing.HQL;
import org.hibernate.annotations.processing.SQL;

import com.thorben.janssen.model.ChessGame;

public interface ChessGameDao {
    
    @HQL("SELECT g FROM ChessGame g WHERE g.playerWhite = :player OR g.playerBlack = :player")
    // @SQL("SELECT * FROM ChessGame g WHERE g.playerWhite = :player OR g.playerBlack = :player")
    List<ChessGame> findGamesByPlayer(String player);

    @Find
    List<ChessGame> generatedFind(String playerWhite);
}
