package com.example.wbcorps.ecom.dto;

import com.example.wbcorps.ecom.entity.CartItems;
import com.example.wbcorps.ecom.entity.User;
import com.example.wbcorps.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private Long id ;
    private String orderDescription ;
    private Date date ;
    private Long amount ;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount ;
    private Long discount ;
    private UUID trackingId ;
    private String username;
    private String address ;
    private List<CartItemsDto> cartItems ;
    private String couponName ;
}
