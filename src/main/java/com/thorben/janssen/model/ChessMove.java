package com.thorben.janssen.model;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class ChessMove {
    
    @EmbeddedId
    private MoveId id;

    private int moveNumber;

    @Embedded
    // @JdbcTypeCode(SqlTypes.JSON)
    private Move move;

    @ManyToOne
    private ChessGame game;

    @Version
    private int version;

    public void setId(MoveId id) {
        this.id = id;
    }

    public MoveId getId() {
        return id;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public int getVersion() {
        return version;
    }
}
