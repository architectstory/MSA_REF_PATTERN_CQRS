package com.samsungsds.msa.biz.order.query.application.dispatcher;

import com.samsungsds.msa.biz.order.query.application.queries.FindAllOrdersQuery;
import com.samsungsds.msa.biz.order.query.application.queries.FindOrderByIdQuery;
import com.samsungsds.msa.biz.order.query.application.queries.FindOrderByOriginQuery;
import com.samsungsds.msa.cqrs.eventsourcing.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllOrdersQuery query);

    List<BaseEntity> handle(FindOrderByIdQuery query);

    List<BaseEntity> handle(FindOrderByOriginQuery query);
}
