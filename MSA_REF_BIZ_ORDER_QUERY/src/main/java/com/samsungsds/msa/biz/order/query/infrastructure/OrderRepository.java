package com.samsungsds.msa.biz.order.query.infrastructure;

import com.samsungsds.msa.biz.order.query.infrastructure.OrderDetail;
import com.samsungsds.msa.cqrs.eventsourcing.BaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<OrderDetail, String> {

    List<BaseEntity> findByCost(String cost);

    List<OrderDetail> findByOrigin(String name);
}
