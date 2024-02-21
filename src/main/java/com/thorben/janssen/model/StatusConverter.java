package com.thorben.janssen.model;

import jakarta.persistence.AttributeConverter;

public class StatusConverter implements AttributeConverter<Boolean, String>{

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? "active" : "inactive";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return dbData.equals("active");
    }
    
}
