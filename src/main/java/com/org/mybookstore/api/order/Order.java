package com.org.mybookstore.api.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.org.mybookstore.api.user.User;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne
 @JoinColumn(name = "user_id")
 private User user;

 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
 private List<OrderItem> items;

 private Date orderDate;
 private double totalAmount;
 private String status ;
 
 
}

enum Status {
    ORDERED,
    SHIPPED,
    DELIVERED;
}


