package com.org.mybookstore.api.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.org.mybookstore.api.user.User;
import com.org.mybookstore.api.user.UserService;
import com.org.mybookstore.api.util.JwtUtil;
import com.org.mybookstore.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtils;

	@GetMapping
	public ResponseEntity<?> getUserOrders(HttpServletRequest request) {

		String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
			String username = jwtUtils.extractUsername(token);
			User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
			return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUser(user));
	}

	@PostMapping("/checkout")
	public ResponseEntity<?> createOrder(HttpServletRequest request) {

		String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
			String username = jwtUtils.extractUsername(token);
			User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
			return ResponseEntity.status(HttpStatus.OK).body("Total amount of Order :"+orderService.createOrder(user).getTotalAmount());
	}

	@PostMapping("/update")
	 @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> updateOrder(HttpServletRequest request, @RequestParam String id, @RequestParam String status) {
			return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrder(id,status));
	}
}
