package com.samsungsds.msa.biz.order.command.infrastructure.producer;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
