package com.samsungsds.msa.cqrs.eventsourcing;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;

import java.util.List;

public interface EventStore {

    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggregateId);
}
