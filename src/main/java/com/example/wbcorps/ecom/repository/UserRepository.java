package com.example.wbcorps.ecom.repository;

import com.example.wbcorps.ecom.entity.User;
import com.example.wbcorps.ecom.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail(String username);

    User findByRole(UserRole userRole);
}
