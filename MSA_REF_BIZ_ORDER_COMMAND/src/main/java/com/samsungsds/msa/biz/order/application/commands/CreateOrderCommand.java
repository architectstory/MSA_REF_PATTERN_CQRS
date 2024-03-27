package com.samsungsds.msa.biz.order.application.commands;

import com.samsungsds.msa.cqrs.dto.BaseCommand;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateOrderCommand extends BaseCommand {

    private String origin;

    private String type;

    private int count;

    private int cost;
}
