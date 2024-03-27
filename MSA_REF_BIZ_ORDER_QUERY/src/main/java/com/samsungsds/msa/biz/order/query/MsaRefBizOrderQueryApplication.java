package com.samsungsds.msa.biz.order;

import com.samsungsds.msa.biz.order.application.queries.FindAllOrdersQuery;
import com.samsungsds.msa.biz.order.application.queries.FindOrderByIdQuery;
import com.samsungsds.msa.biz.order.application.queries.FindOrderByOriginQuery;
import com.samsungsds.msa.biz.order.application.dispatcher.QueryHandler;
import com.samsungsds.msa.cqrs.dispatcher.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.samsungsds.msa.biz.order")
public class MsaRefBizOrderQueryApplication {

    @Autowired
    QueryDispatcher queryDispatcher;

    @Autowired
    QueryHandler queryHandler;

    public static void main(String[] args) {
        SpringApplication.run(MsaRefBizOrderQueryApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandler(FindAllOrdersQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindOrderByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindOrderByOriginQuery.class, queryHandler::handle);
    }

}