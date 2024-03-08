package com.samsungsds.msa.cqrs.dispatcher;

import com.samsungsds.msa.cqrs.dto.BaseCommand;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void sendMessage(BaseCommand command);
}
