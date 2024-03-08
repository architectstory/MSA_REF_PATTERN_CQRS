package com.samsungsds.msa.biz.order.query.infrastructure.handler;

import com.samsungsds.msa.biz.order.query.domain.event.OrderUpdatedEvent;
import com.samsungsds.msa.biz.order.query.infrastructure.OrderDetail;
import com.samsungsds.msa.biz.order.query.infrastructure.OrderRepository;
import com.samsungsds.msa.biz.order.query.domain.event.OrderCreatedEvent;
import com.samsungsds.msa.biz.order.query.domain.event.OrderCancelledEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Service
public class OrderEventHandler implements EventHandler{
    private final Logger logger = LogManager.getLogger(OrderEventHandler.class.getName());

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void on(OrderCreatedEvent event) {
        logger.debug("OrderCreatedEvent");

        var order = OrderDetail.builder()
                .id(event.getId())
                .origin(event.getOrigin())
                .type(event.getType())
                .cost(event.getCost())
                .count(event.getCount())
                .createdDate(event.getCreatedDate())
                .build();

        logger.debug(order.getId());
        logger.debug(order.getType());
        logger.debug(order.getCost());

        orderRepository.save(order);

    }

    @Override
    public void on(OrderUpdatedEvent event) {
        logger.debug("OrderUpdatedEvent");

        var orderExists = orderRepository.findById(event.getId());
        if(orderExists.isEmpty()) {
            logger.debug("OrderUpdatedEvent isEmpty");
            return;
        }


        orderExists.get().setOrigin(event.getOrigin());
        orderExists.get().setType(event.getType());
        orderExists.get().setCost(event.getCost());
        orderExists.get().setCount(event.getCount());

        logger.debug(orderExists.get().getId());
        logger.debug(orderExists.get().getType());
        logger.debug(orderExists.get().getCost());

        orderRepository.save(orderExists.get());
    }

    @Override
    public void on(OrderCancelledEvent event) {

        var orderExists = orderRepository.findById(event.getId());
        logger.debug("OrderCancelledEvent");
        if(orderExists.isEmpty()) {
            logger.debug("OrderCancelledEvent isEmpty");
            return;
        }

        //orderRepository.deleteById(event.getId());

        logger.debug(orderExists.get().getId());
        logger.debug(orderExists.get().getType());
        logger.debug(orderExists.get().getCost());

        orderRepository.delete(orderExists.get());
    }
}
