package com.thorben.janssen.model;

import org.hibernate.annotations.EmbeddableInstantiator;

import jakarta.persistence.Embeddable;

@Embeddable
// @EmbeddableInstantiator(MoveInstantiator.class)
public record Move(MoveColor color, String move) {}
