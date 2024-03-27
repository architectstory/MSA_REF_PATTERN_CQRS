package com.samsungsds.msa.biz.order.infrastructure.producer;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer implements EventProducer {

    private final Logger logger = LogManager.getLogger(OrderEventProducer.class);

    @Value("Message sent to Kafka -> %s")
    private  String kafkaPublishMsg;

    @Autowired
    KafkaTemplate<String, BaseEvent> kafkaTemplate;
    @Override
    @Retryable(maxAttemptsExpression = "${retry.maxAttempts}",
             backoff = @Backoff(maxDelayExpression = "${retry.maxDelay}"))
    public void produce(String topic, BaseEvent event) {
        logger.debug(String.format(kafkaPublishMsg, event));
        kafkaTemplate.send(topic, event);
    }
}
