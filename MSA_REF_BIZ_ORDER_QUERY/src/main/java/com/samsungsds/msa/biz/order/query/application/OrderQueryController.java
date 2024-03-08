package com.samsungsds.msa.biz.order.query.application;


import com.samsungsds.msa.biz.order.query.infrastructure.OrderDetail;
import com.samsungsds.msa.biz.order.query.infrastructure.exceptions.OrderNotFoundException;
import com.samsungsds.msa.biz.order.query.application.queries.*;
import com.samsungsds.msa.cqrs.dispatcher.QueryDispatcher;
import com.samsungsds.msa.cqrs.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/order/")
public class OrderQueryController {

    private String test;

    @Value("No order data found")
    private String noDataFoundErrorMsg;

    @Value("Successfully returned {0} order(s)!")
    private String dataFoundSuccessMsg;

    @Value("No order data found for id - {0}")
    private String dataNotFoundForIdErrorMsg;

    @Value("No order data found for origin - {0}")
    private String dataNotFoundForOriginErrorMsg;

    @Autowired
    QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllOrders() {
        List<OrderDetail> orders = queryDispatcher.sendMessage(new FindAllOrdersQuery());
        if(orders == null || orders.size()==0) {
            throw new OrderNotFoundException(noDataFoundErrorMsg);
        }

        var response = OrderQueryResponse.builder()
                .orders(orders)
                .message(MessageFormat.format(dataFoundSuccessMsg, orders.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<BaseResponse> getOrderById(@PathVariable String id) {
        List<OrderDetail> orders = queryDispatcher.sendMessage(new FindOrderByIdQuery(id));
        if(orders == null || orders.size()==0) {
            var errorMessage = MessageFormat.format(dataNotFoundForIdErrorMsg, id);
            throw new OrderNotFoundException(errorMessage);
        }

        var response = OrderQueryResponse.builder()
                .orders(orders)
                .message(MessageFormat.format(dataFoundSuccessMsg, orders.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("byOrigin/{origin}")
    public ResponseEntity<BaseResponse> getOrderByOrigin(@PathVariable String origin) {
        List<OrderDetail> orders = queryDispatcher.sendMessage(new FindOrderByOriginQuery(origin));
        if(orders == null || orders.size() == 0) {
            var errorMessage = MessageFormat.format(dataNotFoundForOriginErrorMsg, origin);
            throw new OrderNotFoundException(errorMessage);
        }

        var response = OrderQueryResponse.builder()
                .orders(orders)
                .message(MessageFormat.format(dataFoundSuccessMsg, orders.size()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
