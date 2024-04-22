package com.thorben.janssen.model;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.annotations.TenantId;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity
// @SoftDelete
// @SoftDelete(strategy = SoftDeleteType.ACTIVE, columnName = "status", converter = StatusConverter.class)
@NamedQuery(name = "#findGamesOfPlayer", query = "SELECT g FROM ChessGame g WHERE g.playerWhite = :player OR g.playerBlack = :player")
public class ChessGame {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * NATIVE: column type timestamp_with_timezone
     * NORMALIZE: normalize to server timezone (= Hibernate 5)
     * NORMALIZE_UTC: normalize to UTC
     * COLUMN: 1 column timestamp, 1 column offset
     * AUTO: depends on dialect, NATIVE or COLUMN
     * DEFAULT: depends on dialect, NATIVE or NORMALIZE_UTC (>= Hibernate 6.2)
     */
    @TimeZoneStorage(TimeZoneStorageType.DEFAULT)
    private ZonedDateTime date;

    private int round;

    private String playerWhite;
    
    private String playerBlack;

    @OneToMany(mappedBy = "game")
    private Set<ChessMove> moves = new HashSet<>();

    // @TenantId
    // private String tenantId;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getVersion() {
        return version;
    }

    public Set<ChessMove> getMoves() {
        return moves;
    }

    public void setMoves(Set<ChessMove> moves) {
        this.moves = moves;
    }

    public String getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(String playerWhite) {
        this.playerWhite = playerWhite;
    }

    public String getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(String playerBlack) {
        this.playerBlack = playerBlack;
    }

    @Override
    public String toString() {
        return "ChessGame [id=" + id + ", date=" + date + ", round=" + round + ", playerWhite=" + playerWhite
                + ", playerBlack=" + playerBlack + ", version=" + version + "]";
    }

    
}