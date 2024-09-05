package com.thorben.janssen.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
@IdClass(MoveId.class)
public class ChessMove {
    
    // @EmbeddedId
    // private MoveId id;

    @Id
    private UUID id;

    private int moveNumber;

    @Embedded
    // @JdbcTypeCode(SqlTypes.JSON)
    private Move move;

    @ManyToOne
    private ChessGame game;

    @Version
    private int version;

    // public void setId(MoveId id) {
    //     this.id = id;
    // }

    // public MoveId getId() {
    //     return id;
    // }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
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
