package com.samsungsds.msa.biz.order.query;

import com.samsungsds.msa.cqrs.dispatcher.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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