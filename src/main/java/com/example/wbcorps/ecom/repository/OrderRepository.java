package com.example.wbcorps.ecom.repository;

import com.example.wbcorps.ecom.entity.Order;
import com.example.wbcorps.ecom.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {


    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
