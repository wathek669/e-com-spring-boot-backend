package com.example.wbcorps.ecom.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
    private String name ;
    private String email ;
    private String password ;

}
