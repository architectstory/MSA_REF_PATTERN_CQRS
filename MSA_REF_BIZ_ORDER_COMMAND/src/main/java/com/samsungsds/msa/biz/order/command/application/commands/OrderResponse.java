package com.samsungsds.msa.biz.order.command.application.commands;

import com.samsungsds.msa.cqrs.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderResponse extends BaseResponse {
    private String id;

    public OrderResponse(String message, String id) {
        super(message);
        this.id = id;
    }
}
