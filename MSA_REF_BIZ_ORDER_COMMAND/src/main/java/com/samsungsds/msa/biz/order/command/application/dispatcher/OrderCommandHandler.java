package com.samsungsds.msa.biz.order.command.application.dispatcher;

import com.samsungsds.msa.biz.order.command.application.commands.CancelOrderCommand;
import com.samsungsds.msa.biz.order.command.application.commands.CreateOrderCommand;
import com.samsungsds.msa.biz.order.command.application.commands.UpdateOrderCommand;
import com.samsungsds.msa.biz.order.command.domain.OrderAggregate;

import com.samsungsds.msa.cqrs.eventsourcing.EventSourcingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandHandler implements CommandHandler {
    private final Logger logger = LogManager.getLogger(OrderCommandHandler.class.getName());

    @Autowired
    private EventSourcingHandler<OrderAggregate> eventSourcingHandler;

    @Override
    public void handle(CreateOrderCommand command) {
        var aggregate = new OrderAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(UpdateOrderCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.updateOrder(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CancelOrderCommand command){
        var aggregate = eventSourcingHandler.getById(command.getId());

        logger.debug(aggregate.getId());
        logger.debug(aggregate.getVersion());

        aggregate.cancelOrder(command);
        eventSourcingHandler.save(aggregate);
    }
}
