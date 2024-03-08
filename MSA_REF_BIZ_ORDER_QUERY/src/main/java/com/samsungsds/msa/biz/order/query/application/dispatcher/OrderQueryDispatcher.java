package com.samsungsds.msa.biz.order.query.application.dispatcher;

import com.samsungsds.msa.cqrs.eventsourcing.BaseEntity;
import com.samsungsds.msa.cqrs.dto.BaseQuery;
import com.samsungsds.msa.cqrs.dispatcher.QueryDispatcher;
import com.samsungsds.msa.cqrs.dispatcher.QueryHandlerMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class OrderQueryDispatcher implements QueryDispatcher {

    @Value("No Query handler register")
    private String noQueryHandlerFoundErrMsg;

    @Value("Multiple Query handlers defined for a query")
    private String multipleQueryHandlerFoundErrMsg;

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c-> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> sendMessage(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if(handlers == null || handlers.size() ==0 ) {
            throw new RuntimeException(noQueryHandlerFoundErrMsg);
        }

        if(handlers.size() > 1) {
            throw new RuntimeException(multipleQueryHandlerFoundErrMsg);
        }

        return handlers.get(0).handle(query);

    }
}
