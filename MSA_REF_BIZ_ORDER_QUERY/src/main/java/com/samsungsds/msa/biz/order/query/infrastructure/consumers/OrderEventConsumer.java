package com.samsungsds.msa.biz.order.query.infrastructure.consumers;

import com.samsungsds.msa.biz.order.query.infrastructure.handler.EventHandler;
import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer implements EventConsumer {

    private final Logger logger = LogManager.getLogger(OrderEventConsumer.class.getName());

    @Value("Getting an event from Kafka -> %s")
    private String consumeKafkaMsg;
    @Value("Error while consuming the event")
    private String consumeKafkaErrMsg;

    @Autowired
    private EventHandler eventHandler;

    private static final String STR_EVENT_HANDLER_METHOD_NAME ="on";

    @KafkaListener(topics = "msa-topic", groupId = "msa-group")
    @Override
    public void consume(BaseEvent event, Acknowledgment ack) {

    //    logger.info(String.format(consumeKafkaErrMsg, event));
        logger.debug("#### " + event.getId());
        logger.debug("#### " + event.getVersion());
        logger.debug("#### " + eventHandler.getClass().getTypeName());

        try {
            var eventHandlerMethod = eventHandler.getClass().getDeclaredMethod(STR_EVENT_HANDLER_METHOD_NAME, event.getClass());
            eventHandlerMethod.setAccessible(true);
            eventHandlerMethod.invoke(eventHandler, event);
            ack.acknowledge();
        } catch (Exception e) {
            throw new RuntimeException(consumeKafkaErrMsg + e.toString());
        }
    }
}
