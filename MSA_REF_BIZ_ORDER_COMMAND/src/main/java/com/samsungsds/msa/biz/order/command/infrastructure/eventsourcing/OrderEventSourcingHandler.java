package com.samsungsds.msa.biz.order.command.infrastructure.eventsourcing;

import com.samsungsds.msa.biz.order.command.domain.OrderAggregate;
import com.samsungsds.msa.cqrs.aggregate.AggregateRoot;
import com.samsungsds.msa.cqrs.eventsourcing.EventSourcingHandler;
import com.samsungsds.msa.cqrs.eventsourcing.EventStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class OrderEventSourcingHandler implements EventSourcingHandler<OrderAggregate> {

    private final Logger logger = LogManager.getLogger(OrderEventSourcingHandler.class.getName());

    @Autowired
    private EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        logger.debug(aggregate.getId());
        logger.debug(aggregate.getVersion());

        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public OrderAggregate getById(String id) {
        var aggregate = new OrderAggregate();
        var events = eventStore.getEvents(id);

        if(events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            latestVersion.ifPresent(aggregate::setVersion);

            logger.debug("latestVersion " + latestVersion.get().toString());
        }
        return aggregate;
    }
}
