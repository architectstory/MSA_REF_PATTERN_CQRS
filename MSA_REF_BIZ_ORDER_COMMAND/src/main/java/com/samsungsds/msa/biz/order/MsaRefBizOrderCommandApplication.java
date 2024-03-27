package com.samsungsds.msa.biz.order;

import com.samsungsds.msa.biz.order.application.dispatcher.CommandHandler;
import com.samsungsds.msa.biz.order.application.commands.CancelOrderCommand;
import com.samsungsds.msa.biz.order.application.commands.UpdateOrderCommand;
import com.samsungsds.msa.biz.order.application.commands.CreateOrderCommand;
import com.samsungsds.msa.cqrs.dispatcher.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages="com.samsungsds.msa.biz.order")
@EnableRetry
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class MsaRefBizOrderCommandApplication {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private CommandHandler commandHandler;

    public static void main(String[] args) {
        SpringApplication.run(MsaRefBizOrderCommandApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(CreateOrderCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(UpdateOrderCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CancelOrderCommand.class, commandHandler::handle);
    }
}