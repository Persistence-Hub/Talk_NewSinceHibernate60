package com.thorben.janssen.model;

import org.hibernate.annotations.EmbeddableInstantiator;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

@Embeddable
// @EmbeddableInstantiator(MoveInstantiator.class)
public record Move(MoveColor color, String move) {}
// public class Move {
    
//     private MoveColor color;

//     private String move;

//     public Move() {}

//     public Move(MoveColor color, String move) {
//         this.color = color;
//         this.move = move;
//     }

//     public MoveColor getColor() {
//         return color;
//     }

//     public void setColor(MoveColor color) {
//         this.color = color;
//     }

//     public String getMove() {
//         return move;
//     }

//     public void setMove(String move) {
//         this.move = move;
//     }

//     @Override
//     public String toString() {
//         return "Move [color=" + color + ", move=" + move + "]";
//     }
// }
