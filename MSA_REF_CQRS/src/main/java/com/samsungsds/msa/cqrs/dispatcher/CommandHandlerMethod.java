package com.samsungsds.msa.cqrs.dispatcher;

import com.samsungsds.msa.cqrs.dto.BaseCommand;

@FunctionalInterface
public interface CommandHandlerMethod <T extends BaseCommand>{
    void handle(T command);
}
