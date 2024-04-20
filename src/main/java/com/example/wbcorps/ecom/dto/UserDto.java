package com.example.wbcorps.ecom.dto;

import com.example.wbcorps.ecom.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id ;
    private String email ;
    private String name ;
    private UserRole userRole;
}
