package com.org.mybookstore.api.book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.mybookstore.api.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/books")
public class BookController {

 @Autowired
 private BookService bookService;
 
 @Autowired
 private JwtUtil jwtUtils;

 @GetMapping("/fetchAll")
 @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
 public ResponseEntity<?> getAllBooks(HttpServletRequest request) {
	 
	return  ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
 }

 @GetMapping("/{id}")
 @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
 public Book getBookById(@PathVariable Long id,HttpServletRequest request) {
	
	return	 bookService.getBookById(id).get();
 }
 

 @PostMapping("/create")
 @PreAuthorize("hasAuthority('ADMIN')")
 public Book createBook(@RequestBody Book book,HttpServletRequest request) {
     
	
	return	bookService.saveBook(book);
 }
 
 @PostMapping("/update")
 @PreAuthorize("hasAuthority('ADMIN')")
 public ResponseEntity<?> updateBook(@RequestBody Book updatedbook,HttpServletRequest request) {
	 
	
	 Book book = bookService.getBookById(updatedbook.getId()).get();
	 book.setAuthor(updatedbook.getAuthor());
	 book.setDescription(updatedbook.getDescription());
	 book.setTitle(updatedbook.getTitle());
	 book.setPrice(updatedbook.getPrice());
	 return ResponseEntity
                 .ok()
                 .body(bookService.saveBook(book));
	
     
		 
 }

 @DeleteMapping("/{id}")
 @PreAuthorize("hasAuthority('ADMIN')")
 public ResponseEntity<?> deleteBook(@PathVariable Long id,HttpServletRequest request) {
	 
	bookService.deleteBook(id);
	return  ResponseEntity
     .ok()
     .body("Book deleted succesfully");
	
 }
}

