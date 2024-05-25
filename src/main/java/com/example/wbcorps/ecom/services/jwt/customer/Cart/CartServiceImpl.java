package com.example.wbcorps.ecom.services.jwt.customer.Cart;

import com.example.wbcorps.ecom.Exceptions.ValidationException;
import com.example.wbcorps.ecom.dto.AddProductInCartDto;
import com.example.wbcorps.ecom.dto.CartItemsDto;
import com.example.wbcorps.ecom.dto.OrderDto;
import com.example.wbcorps.ecom.entity.*;
import com.example.wbcorps.ecom.enums.OrderStatus;
import com.example.wbcorps.ecom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    CartItemsRepository cartItemsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CouponRepository couponRepository;
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId
                (addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());
        if (optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());
            if (optionalProduct.isPresent()) {
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);
                CartItems updatedCart = cartItemsRepository.save(cart);
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);
                orderRepository.save(activeOrder);
                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user or product not found");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);

        List<CartItemsDto> cartItemsDtoList =activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        if (activeOrder.getCoupon()!=null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }
        return orderDto;
    }

    public OrderDto applyCoupon(Long userId,String code ){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found!"));
        if (couponIsExpired(coupon)){
            throw  new ValidationException("coupon expired!");
        }
        double discountAmount =((coupon.getDiscount()/100.0) *activeOrder.getTotalAmount());
        double netAmount =activeOrder.getTotalAmount()-discountAmount;
        activeOrder.setAmount((long)netAmount );
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);
        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();

    }

    private boolean couponIsExpired(Coupon coupon){
        Date today=new Date();
        Date expDate = coupon.getExpirationDate();
        return expDate!=null && today.after(expDate);
    }

    public OrderDto increaseProductQte(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId()
        );
        if (optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems=optionalCartItems.get();
            Product product =optionalProduct.get();
            activeOrder.setAmount(activeOrder.getAmount()+product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount()+product.getPrice());
            cartItems.setQuantity(cartItems.getQuantity()+1);
            if(activeOrder.getCoupon()!=null){
                double discountAmount =((activeOrder.getCoupon().getDiscount()/100.0) *activeOrder.getTotalAmount());
                double netAmount =activeOrder.getTotalAmount()-discountAmount;
                activeOrder.setAmount((long)netAmount );
                activeOrder.setDiscount((long)discountAmount);
            }
            cartItemsRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;

    }
    public OrderDto decreaseProductQte(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId()
        );
        if (optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems=optionalCartItems.get();
            Product product =optionalProduct.get();
            activeOrder.setAmount(activeOrder.getAmount()-product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount()-product.getPrice());
            cartItems.setQuantity(cartItems.getQuantity()-1);
            if(activeOrder.getCoupon()!=null){
                double discountAmount =((activeOrder.getCoupon().getDiscount()/100.0) *activeOrder.getTotalAmount());
                double netAmount =activeOrder.getTotalAmount()-discountAmount;
                activeOrder.setAmount((long)netAmount );
                activeOrder.setDiscount((long)discountAmount);
            }
            cartItemsRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;

    }

}

