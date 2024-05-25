package com.example.wbcorps.ecom.entity;

import com.example.wbcorps.ecom.dto.OrderDto;
import com.example.wbcorps.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String orderDescription ;
    private Date date ;
    private Long amount ;
    private String payment;
    private OrderStatus orderStatus;
    private String address;
    private Long totalAmount ;
    private Long discount ;
    private UUID trackingId ;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id",referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems ;

    public OrderDto getOrderDto(){
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(amount);
        orderDto.setId(id);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setAddress(address);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setTrackingId(trackingId);
        orderDto.setUsername(user.getName());
        if (coupon !=null){
            orderDto.setCouponName(coupon.getName());
        }
        return orderDto;
    }
}
