package com.org.mybookstore.api.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.mybookstore.api.user.User;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
 List<CartItem> findByUser(User user);
}

