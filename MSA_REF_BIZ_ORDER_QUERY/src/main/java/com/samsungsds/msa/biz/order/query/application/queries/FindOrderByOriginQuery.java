package com.samsungsds.msa.biz.order.query.application.queries;

import com.samsungsds.msa.cqrs.dto.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FindOrderByOriginQuery extends BaseQuery {
    private String origin;
}
