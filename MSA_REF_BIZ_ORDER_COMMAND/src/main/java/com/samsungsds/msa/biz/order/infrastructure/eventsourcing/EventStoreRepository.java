package com.samsungsds.msa.biz.order.infrastructure.eventsourcing;

import com.samsungsds.msa.biz.order.infrastructure.dto.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStoreRepository extends JpaRepository<EventModel, String> {

    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
