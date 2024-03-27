package com.samsungsds.msa.biz.order.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;
import jakarta.persistence.AttributeConverter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseEventConverter implements AttributeConverter <BaseEvent, String> {

    private final Logger logger = Logger.getLogger(BaseEventConverter.class.getName());

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(BaseEvent attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        }catch (JsonProcessingException e) {
            logger.log(Level.SEVERE,"Error while converting BaseEvent to JSON String", e);
            throw new RuntimeException("Error while converting BaseEvent to JSON String", e);
        }
    }

    @Override
    public BaseEvent convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, OrderCreatedEvent.class);
        }catch (JsonProcessingException e) {
            logger.log(Level.SEVERE,"Error while converting JSON string to BaseEvent", e);
            throw new RuntimeException("Error while converting JSON string to BaseEvent", e);
        }
    }
}
