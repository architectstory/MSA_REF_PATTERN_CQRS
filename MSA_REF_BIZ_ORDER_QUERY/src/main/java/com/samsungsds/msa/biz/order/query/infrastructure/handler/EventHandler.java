package com.samsungsds.msa.biz.order.query.infrastructure.handler;

import com.samsungsds.msa.biz.order.query.domain.event.OrderUpdatedEvent;
import com.samsungsds.msa.biz.order.query.domain.event.OrderCreatedEvent;
import com.samsungsds.msa.biz.order.query.domain.event.OrderCancelledEvent;

public interface EventHandler {

    void on(OrderCreatedEvent event);
    void on(OrderUpdatedEvent event);
    void on(OrderCancelledEvent event);

}
