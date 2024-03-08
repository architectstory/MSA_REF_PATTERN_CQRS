package com.samsungsds.msa.biz.order.command.application;

import com.samsungsds.msa.biz.order.application.dto.*;
import com.samsungsds.msa.biz.order.command.application.commands.*;
import com.samsungsds.msa.cqrs.dispatcher.CommandDispatcher;

import com.samsungsds.msa.cqrs.dto.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/rest/api/v1/order/")
@Log4j2
public class OrderController {
    private final Logger logger = LogManager.getLogger(OrderController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Value("Order created successfully")
    private String orderCreateMsg;

    @Value("Order updated successfully")
    private String orderUpdateMsg;

    @Value("Order cancelled successfully")
    private String orderCancelMsg;

    @PostMapping
    public ResponseEntity<BaseResponse> createOrder(@RequestBody CreateOrderCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        logger.debug("### createOrder");
        commandDispatcher.sendMessage(command);
        return new ResponseEntity<>(new OrderResponse(orderCreateMsg,id), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> updateOrder(@PathVariable String id, @RequestBody UpdateOrderCommand command) {
        command.setId(id);
        logger.debug("### updateOrder");
        commandDispatcher.sendMessage(command);
        return new ResponseEntity<>(new OrderResponse(orderUpdateMsg, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> cancelOrder(@PathVariable String id, @RequestBody CancelOrderCommand command) {
        command.setId(id);
        logger.debug("### cancelOrder");
        commandDispatcher.sendMessage(command);
        return new ResponseEntity<>(new OrderResponse(orderCancelMsg, id), HttpStatus.OK);
    }
}
