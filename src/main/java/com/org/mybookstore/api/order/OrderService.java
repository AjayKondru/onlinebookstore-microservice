package com.org.mybookstore.api.order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.mybookstore.api.cart.CartItem;
import com.org.mybookstore.api.cart.CartRepository;
import com.org.mybookstore.api.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

 @Autowired
 private OrderRepository orderRepository;

 @Autowired
 private OrderItemRepository orderItemRepository;

 @Autowired
 private CartRepository cartItemRepository;

 public List<OrderResponse> getOrdersByUser(User user) {
	 List<Order> orderList =  orderRepository.findByUser(user);
	 List<OrderResponse> responseList = new ArrayList<>();
	 responseList= orderList.stream().map(order -> new OrderResponse(order.getId(), order.getOrderDate(), order.getTotalAmount(), order.getStatus())).toList();
	 return responseList;
 }
 
 public OrderResponse updateOrder(String id, String status) {
     Order order =  orderRepository.findByid( Long.valueOf(id));
     order.setStatus(status);
     Order savedOrder = orderRepository.save(order);
     return new OrderResponse(savedOrder.getId(), savedOrder.getOrderDate(), savedOrder.getTotalAmount(), savedOrder.getStatus());
 }

 public Order createOrder(User user) {
     List<CartItem> cartItems = cartItemRepository.findByUser(user);

     if (cartItems.isEmpty()) {
         throw new RuntimeException("Cart is empty");
     }

     Order order = new Order();
     order.setUser(user);
     order.setOrderDate(new Date());
     order.setTotalAmount(cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getBook().getPrice()).sum());

     List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
         OrderItem orderItem = new OrderItem();
         orderItem.setBook(cartItem.getBook());
         orderItem.setOrder(order);
         orderItem.setQuantity(cartItem.getQuantity());
         orderItem.setPrice(cartItem.getBook().getPrice());
         return orderItem;
     }).collect(Collectors.toList());

     order.setItems(orderItems);
     order.setStatus(Status.ORDERED.toString());
     Order savedOrder = orderRepository.save(order);
     orderItemRepository.saveAll(orderItems);
     cartItemRepository.deleteAll(cartItems);

     return savedOrder;
 }
}

