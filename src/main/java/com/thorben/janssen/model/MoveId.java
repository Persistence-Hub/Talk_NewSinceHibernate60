package com.thorben.janssen.model;

import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public record MoveId(UUID id) {

}
