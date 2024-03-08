package com.samsungsds.msa.cqrs.dispatcher;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEntity;
import com.samsungsds.msa.cqrs.dto.BaseQuery;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T query);
}
