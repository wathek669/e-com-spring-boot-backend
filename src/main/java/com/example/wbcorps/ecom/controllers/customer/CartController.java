package com.example.wbcorps.ecom.controllers.customer;

import com.example.wbcorps.ecom.Exceptions.ValidationException;
import com.example.wbcorps.ecom.dto.AddProductInCartDto;
import com.example.wbcorps.ecom.dto.OrderDto;
import com.example.wbcorps.ecom.services.jwt.customer.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService ;
    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto){
        return cartService.addProductToCart(addProductInCartDto) ;
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Long userId){
        OrderDto orderDto =cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId,@PathVariable String code ){
        try{
            OrderDto orderDto=cartService.applyCoupon(userId,code);
            return ResponseEntity.ok(orderDto);
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/addition")
    public ResponseEntity<?> increaseProductQte(@RequestBody AddProductInCartDto addProductInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQte(addProductInCartDto)) ;
    }
    @PostMapping("/deduction")
    public ResponseEntity<?> decreaseProductQte(@RequestBody AddProductInCartDto addProductInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQte(addProductInCartDto)) ;
    }
}
