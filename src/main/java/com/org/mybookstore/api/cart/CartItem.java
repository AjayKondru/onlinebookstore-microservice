package com.org.mybookstore.api.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.org.mybookstore.api.book.Book;
import com.org.mybookstore.api.user.User;

import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne
 @JoinColumn(name = "book_id")
 private Book book;

 @ManyToOne
 @JoinColumn(name = "user_id")
 private User user;

 private int quantity;
}

