package com.samsungsds.msa.cqrs.dispatcher;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEntity;
import com.samsungsds.msa.cqrs.dto.BaseQuery;

import java.util.List;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> sendMessage(BaseQuery query);

}
