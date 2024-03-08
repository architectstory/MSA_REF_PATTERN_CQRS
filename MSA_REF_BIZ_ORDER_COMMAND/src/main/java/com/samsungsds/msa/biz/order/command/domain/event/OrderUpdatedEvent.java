package com.samsungsds.msa.biz.order.command.domain.event;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderUpdatedEvent extends BaseEvent {

    private String origin;

    private String type;

    private int count;

    private int cost;

    private Date createdDate;
}
