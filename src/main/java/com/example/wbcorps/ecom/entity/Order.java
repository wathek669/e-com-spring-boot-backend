package com.example.wbcorps.ecom.entity;

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
    private Long totalAmount ;
    private Long discount ;
    private UUID trackingId ;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems ;

}
