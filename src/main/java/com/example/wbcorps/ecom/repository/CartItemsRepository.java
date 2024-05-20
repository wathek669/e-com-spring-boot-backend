package com.example.wbcorps.ecom.repository;

import com.example.wbcorps.ecom.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
    Optional<CartItems> findByProductOrderAndUserId(Long productId, Long id, Long userId);
}
