package com.samsungsds.msa.biz.order.query.infrastructure.consumers;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload BaseEvent event, Acknowledgment ack);
}
