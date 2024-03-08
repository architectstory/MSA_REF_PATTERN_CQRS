package com.samsungsds.msa.biz.order.query.application.queries;

import com.samsungsds.msa.biz.order.query.infrastructure.OrderDetail;
import com.samsungsds.msa.cqrs.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderQueryResponse extends BaseResponse {
    private List<OrderDetail> orders;

    public OrderQueryResponse(String message) { super(message);}
}
