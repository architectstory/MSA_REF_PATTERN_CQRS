package com.samsungsds.msa.biz.order.query.domain.event;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderUpdatedEvent extends BaseEvent {
    private String origin;

    private String type;

    private int count;

    private int cost;

    private Date createdDate;

}
