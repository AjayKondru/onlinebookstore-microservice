package com.org.mybookstore.api.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.mybookstore.api.user.User;
import com.org.mybookstore.api.user.UserService;
import com.org.mybookstore.api.util.JwtUtil;
import com.org.mybookstore.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtils;

	@GetMapping
	public ResponseEntity<?> getCartItems(HttpServletRequest request) {

		String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
		if (jwtUtils.validateJwtToken(token)) {
			String username = jwtUtils.extractUsername(token);
			User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
			return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartItems(user));
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/add")
	public ResponseEntity<?> addBookToCart(HttpServletRequest request, @RequestParam Long bookId,
			@RequestParam int quantity) {
		String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
		if (jwtUtils.validateJwtToken(token)) {
			String username = jwtUtils.extractUsername(token);
			User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
			return ResponseEntity.status(HttpStatus.OK).body(cartService.addBookToCart(bookId, quantity, user));
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@DeleteMapping("/remove/{cartItemId}")
	public ResponseEntity<?> removeBookFromCart(HttpServletRequest request, @PathVariable Long cartItemId) {

		String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
		if (jwtUtils.validateJwtToken(token)) {
			String username = jwtUtils.extractUsername(token);
			User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
			cartService.removeBookFromCart(cartItemId, user);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@DeleteMapping("/clear")
	public ResponseEntity<?> clearCart(HttpServletRequest request) {

		String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
		if (jwtUtils.validateJwtToken(token)) {
			String username = jwtUtils.extractUsername(token);
			User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
			cartService.clearCart(user);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
