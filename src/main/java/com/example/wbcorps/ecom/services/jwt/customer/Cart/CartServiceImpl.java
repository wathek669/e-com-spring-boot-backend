package com.example.wbcorps.ecom.services.jwt.customer.Cart;

import com.example.wbcorps.ecom.dto.AddProductInCartDto;
import com.example.wbcorps.ecom.entity.CartItems;
import com.example.wbcorps.ecom.entity.Order;
import com.example.wbcorps.ecom.entity.Product;
import com.example.wbcorps.ecom.entity.User;
import com.example.wbcorps.ecom.enums.OrderStatus;
import com.example.wbcorps.ecom.repository.CartItemsRepository;
import com.example.wbcorps.ecom.repository.OrderRepository;
import com.example.wbcorps.ecom.repository.ProductRepository;
import com.example.wbcorps.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    CartItemsRepository cartItemsRepository;
    @Autowired
    private ProductRepository productRepository ;
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
        Order activeOrder =  orderRepository.findByIdAndStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductOrderAndUserId
                (addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId());
        if(optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null) ;
        }else {
            Optional<Product> optionalProduct= productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId()) ;
            if (optionalProduct.isPresent()){
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);
                CartItems updatedCart =cartItemsRepository.save(cart) ;
                activeOrder.setTotalAmount(activeOrder.getTotalAmount()+cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount()+cart.getPrice());
                activeOrder.getCartItems().add(cart);
                orderRepository.save(activeOrder);
                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user or product not found") ;
            }
        }
    }
}
