package com.org.mybookstore.api.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.mybookstore.exceptions.BookNotFoundException;
import com.org.mybookstore.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

 @Autowired
 private BookRepository bookRepository;

 public List<Book> getAllBooks() {
     return bookRepository.findAll();
 }

 public Optional<Book> getBookById(Long id) {
     return Optional.of(bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found")));
 }

 public Book saveBook(Book book) {
     return bookRepository.save(book);
 }

 public void deleteBook(Long id) {
	 Optional.of(bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found")));
     bookRepository.deleteById(id);
 }

public List<Book> getBooks() {
	return bookRepository.findAll();
}
}
