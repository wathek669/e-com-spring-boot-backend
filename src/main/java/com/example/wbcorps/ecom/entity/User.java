package com.example.wbcorps.ecom.entity;

import com.example.wbcorps.ecom.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@Table(name = "users")
@Slf4j
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email ;
    private String password;
    private String name ;
    private UserRole role ;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img ;
}
