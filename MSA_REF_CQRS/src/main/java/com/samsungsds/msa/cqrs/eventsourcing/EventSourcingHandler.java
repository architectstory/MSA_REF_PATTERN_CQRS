package com.samsungsds.msa.cqrs.eventsourcing;

import com.samsungsds.msa.cqrs.aggregate.AggregateRoot;

public interface EventSourcingHandler <T> {

    void save(AggregateRoot aggregate);
    T getById(String id);
}
