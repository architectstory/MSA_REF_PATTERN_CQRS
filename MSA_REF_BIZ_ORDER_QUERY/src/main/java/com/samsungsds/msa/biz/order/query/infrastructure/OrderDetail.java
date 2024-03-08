package com.samsungsds.msa.biz.order.query.infrastructure;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@Builder
@Document(collection = "ORDER")
public class OrderDetail extends BaseEntity {

    private String id;

    private String origin;

    private String type;

    private int count;

    private int cost;

    private Date createdDate;
}
