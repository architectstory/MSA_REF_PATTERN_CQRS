package com.samsungsds.msa.biz.order.application.dispatcher;

import com.samsungsds.msa.biz.order.application.commands.CancelOrderCommand;
import com.samsungsds.msa.biz.order.application.commands.CreateOrderCommand;
import com.samsungsds.msa.biz.order.application.commands.UpdateOrderCommand;

public interface CommandHandler {

    void handle(CreateOrderCommand command);

    void handle(UpdateOrderCommand command) ;

    void handle(CancelOrderCommand command) ;
}
