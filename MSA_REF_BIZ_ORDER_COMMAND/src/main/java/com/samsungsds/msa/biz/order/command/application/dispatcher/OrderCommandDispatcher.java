package com.samsungsds.msa.biz.order.command.application.dispatcher;

import com.samsungsds.msa.cqrs.dto.BaseCommand;
import com.samsungsds.msa.cqrs.dispatcher.CommandDispatcher;
import com.samsungsds.msa.cqrs.dispatcher.CommandHandlerMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class OrderCommandDispatcher implements CommandDispatcher {
    @Value("No Command handler defined")
    private String commandHandlerNotFoundErrorMsg;
    @Value("Multiple Command Handlers defined for a command")
    private String multipleCommandHandlerNotFoundErrorMsg;

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();


    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c-> new LinkedList<>());
        handlers.add(handler);

    }

    @Override
    public void sendMessage(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if(handlers == null || handlers.size() == 0) {
            throw new RuntimeException(commandHandlerNotFoundErrorMsg);
        }

        if(handlers.size() > 1) {
            throw new RuntimeException(multipleCommandHandlerNotFoundErrorMsg);
        }

        handlers.get(0).handle(command);
    }
}
