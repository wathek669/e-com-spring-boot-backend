package com.example.wbcorps.ecom.services.jwt.customer.Cart;

import com.example.wbcorps.ecom.dto.AddProductInCartDto;
import com.example.wbcorps.ecom.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
    OrderDto getCartByUserId(Long userId);
    OrderDto applyCoupon(Long userId,String code );
    OrderDto increaseProductQte(AddProductInCartDto addProductInCartDto);
    OrderDto decreaseProductQte(AddProductInCartDto addProductInCartDto);
}
