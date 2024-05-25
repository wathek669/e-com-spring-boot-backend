package com.example.wbcorps.ecom.services.jwt.customer.Cart;

import com.example.wbcorps.ecom.dto.AddProductInCartDto;
import com.example.wbcorps.ecom.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
    OrderDto getCartByUserId(Long userId);
}
