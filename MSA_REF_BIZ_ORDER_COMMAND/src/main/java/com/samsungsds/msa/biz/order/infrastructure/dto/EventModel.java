package com.samsungsds.msa.biz.order.infrastructure.dto;

import com.samsungsds.msa.biz.order.domain.BaseEventConverter;
import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="EVENT_STORE_DB")
public class EventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Date timeStamp;
    private String aggregateIdentifier;
    private int version;
    private String eventType;
    @Convert(converter =  BaseEventConverter.class)
    private BaseEvent eventData;
}
