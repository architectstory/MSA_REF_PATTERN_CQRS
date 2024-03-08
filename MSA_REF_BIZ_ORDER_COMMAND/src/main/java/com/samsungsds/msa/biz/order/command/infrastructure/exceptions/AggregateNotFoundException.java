package com.samsungsds.msa.biz.order.command.infrastructure.exceptions;

public class AggregateNotFoundException extends RuntimeException {
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
