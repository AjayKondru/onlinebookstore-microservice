package com.org.mybookstore.api.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.mybookstore.api.book.Book;
import com.org.mybookstore.api.book.BookRepository;
import com.org.mybookstore.api.user.User;

@Service
public class CartService {

	@Autowired
	private CartRepository cartItemRepository;

	@Autowired
	private BookRepository bookRepository;


	public List<CartItem> getCartItems(User user) {
		return cartItemRepository.findByUser(user);
	}

	public CartItem addBookToCart(Long bookId, int quantity, User user) {
		Book book = bookRepository.findById(bookId).get();
		CartItem cartItem = cartItemRepository.findByUser(user).stream()
				.filter(item -> item.getBook().getId().equals(bookId)).findFirst().orElse(new CartItem());

		cartItem.setBook(book);
		cartItem.setUser(user);
		cartItem.setQuantity(cartItem.getId() != null ? cartItem.getQuantity() + quantity : quantity);

		return cartItemRepository.save(cartItem);
	}

	public void removeBookFromCart(Long cartItemId, User user) {
		CartItem cartItem = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Cart item not found"));

		if (cartItem.getUser().getId().equals(user.getId())) {
			cartItemRepository.delete(cartItem);
		} else {
			throw new RuntimeException("Unauthorized access");
		}
	}

	public void clearCart(User user) {
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		cartItemRepository.deleteAll(cartItems);
	}
}
