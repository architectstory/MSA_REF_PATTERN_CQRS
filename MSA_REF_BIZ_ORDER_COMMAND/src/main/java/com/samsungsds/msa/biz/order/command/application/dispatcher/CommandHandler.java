package com.samsungsds.msa.biz.order.command.application.dispatcher;

import com.samsungsds.msa.biz.order.command.application.commands.CancelOrderCommand;
import com.samsungsds.msa.biz.order.command.application.commands.CreateOrderCommand;
import com.samsungsds.msa.biz.order.command.application.commands.UpdateOrderCommand;

public interface CommandHandler {

    void handle(CreateOrderCommand command);

    void handle(UpdateOrderCommand command) ;

    void handle(CancelOrderCommand command) ;
}
