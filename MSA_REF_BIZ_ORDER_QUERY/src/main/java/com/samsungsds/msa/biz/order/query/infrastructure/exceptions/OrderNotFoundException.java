package com.samsungsds.msa.biz.order.query.infrastructure.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) { super(message); }
}
