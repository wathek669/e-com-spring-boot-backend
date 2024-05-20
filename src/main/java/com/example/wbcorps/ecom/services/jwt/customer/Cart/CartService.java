package com.example.wbcorps.ecom.services.jwt.customer.Cart;

import com.example.wbcorps.ecom.dto.AddProductInCartDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
}
