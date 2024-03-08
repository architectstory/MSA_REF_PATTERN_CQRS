package com.samsungsds.msa.biz.order.command.infrastructure.eventsourcing;

import com.samsungsds.msa.biz.order.command.infrastructure.dto.EventModel;
import com.samsungsds.msa.biz.order.command.infrastructure.eventsourcing.EventStoreRepository;
import com.samsungsds.msa.biz.order.command.infrastructure.exceptions.AggregateNotFoundException;
import com.samsungsds.msa.biz.order.command.infrastructure.exceptions.ConcurrencyException;
import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;
import com.samsungsds.msa.biz.order.command.infrastructure.producer.EventProducer;
import com.samsungsds.msa.cqrs.eventsourcing.EventStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderEventStore implements EventStore {

    @Value("Aggregate not found for Id")
    private String aggregateNotFoundMsg;
    @Value("msa-topic")
    private String topicName;

    private final static String STR_COLON = ": ";

    /* EventProducer 생성 후 Open*/
    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    @Transactional
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream != null &&
                expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }

        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);

            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var eventPersisted = eventStoreRepository.save(eventModel);

            /* EventProducer 생성 후 Open*/
            if (!eventPersisted.getId().isEmpty()) {
                eventProducer.produce(topicName, event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
               throw new AggregateNotFoundException(aggregateNotFoundMsg + STR_COLON + aggregateId);
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }
}
