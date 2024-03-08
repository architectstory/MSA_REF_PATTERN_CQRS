package com.samsungsds.msa.biz.order.query.application.dispatcher;

import com.samsungsds.msa.biz.order.query.application.queries.FindAllOrdersQuery;
import com.samsungsds.msa.biz.order.query.application.queries.FindOrderByIdQuery;
import com.samsungsds.msa.biz.order.query.application.queries.FindOrderByOriginQuery;
import com.samsungsds.msa.biz.order.query.infrastructure.OrderRepository;
import com.samsungsds.msa.biz.order.query.infrastructure.OrderDetail;
import com.samsungsds.msa.cqrs.eventsourcing.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderQueryHandler implements QueryHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<BaseEntity> handle(FindAllOrdersQuery query) {

        Iterable<OrderDetail> orderDetails = orderRepository.findAll();

        List<BaseEntity> orderList = new ArrayList<>();
        orderDetails.forEach(orderList::add);
        return orderList;
    }

    @Override
    public List<BaseEntity> handle(FindOrderByIdQuery query) {
        var orderDetails = orderRepository.findById(query.getId());
        if(orderDetails.isEmpty()) {
            return null;
        }
        List<BaseEntity> orderList = new ArrayList<>();
        orderList.add(orderDetails.get());
        return orderList;
    }

    @Override
    public List<BaseEntity> handle(FindOrderByOriginQuery query) {
        var orderDetails = orderRepository.findByOrigin(query.getOrigin());

        if(orderDetails.isEmpty()) {
            return null;
        }
        List<BaseEntity> orderList = new ArrayList<>();
        orderDetails.forEach(orderList::add);
        return orderList;
    }
}
