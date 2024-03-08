package com.samsungsds.msa.biz.order.command.domain;

import com.samsungsds.msa.biz.order.command.application.commands.CancelOrderCommand;
import com.samsungsds.msa.biz.order.command.application.commands.CreateOrderCommand;
import com.samsungsds.msa.biz.order.command.application.commands.UpdateOrderCommand;

import com.samsungsds.msa.biz.order.command.domain.event.OrderCancelledEvent;
import com.samsungsds.msa.biz.order.command.domain.event.OrderCreatedEvent;
import com.samsungsds.msa.biz.order.command.domain.event.OrderUpdatedEvent;
import com.samsungsds.msa.cqrs.aggregate.AggregateRoot;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@NoArgsConstructor
public class OrderAggregate extends AggregateRoot {

    @Value("Order can not be updated as no order exists")
    private String noOrderFoundErrorMsg;
    @Value("An Order has already been cancelled")
    private String orderAlreadyCancelErrorMsg;

    private Boolean isOrderCreated;

    private String origin;
    private String type;
    private int cost;
    private int count;

    public Boolean getIsOrderCreated() { return isOrderCreated; }

    public String getOrigin(){ return origin; }

    public String getType() { return  type; }
    public int getCost() { return  cost; }
    public int getCount() { return count; }

    public OrderAggregate(CreateOrderCommand command) {
        raiseEvent(OrderCreatedEvent.builder()
                .id(command.getId())
                .origin(command.getOrigin())
                .type(command.getType())
                .cost(command.getCost())
                .count(command.getCount())
                .createdDate(new Date())
                .build());
    }

    public void apply(OrderCreatedEvent event) {
        this.isOrderCreated = true;
        this.id = event.getId();
        this.origin = event.getOrigin();
        this.type = event.getType();
        this.count = event.getCount();
        this.cost = event.getCost();
    }

    public void updateOrder(UpdateOrderCommand command) {
        if(!this.isOrderCreated) {
            throw new IllegalStateException(noOrderFoundErrorMsg);
        }
        raiseEvent(OrderUpdatedEvent.builder()
                .id(this.id)
                .origin(command.getOrigin())
                .type(command.getType())
                .cost(command.getCost())
                .count(command.getCount())
                .createdDate(new Date())
                .build());
    }

    public void apply(OrderUpdatedEvent event) {
        this.id = event.getId();
        this.origin = event.getOrigin();
        this.type = event.getType();
        this.count = event.getCount();
        this.cost = event.getCost();
    }

    public void cancelOrder(CancelOrderCommand command) {
        if(!this.isOrderCreated) {
            throw new IllegalStateException(orderAlreadyCancelErrorMsg);
        }

        raiseEvent(OrderCancelledEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(OrderCancelledEvent event) {
        this.id = event.getId();
        this.isOrderCreated = false;
    }
}
