package com.org.mybookstore.api.book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 public ResponseEntity<?> getAllBooks(HttpServletRequest request) {
	 String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
	 if(jwtUtils.validateJwtToken(token))	 
     return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
	 else
	return	 ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		 
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> getBookById(@PathVariable Long id,HttpServletRequest request) {
	 String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
	 if(jwtUtils.validateJwtToken(token))	 
     return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id).get());
	 else
	return	 ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
 }
 

 @PostMapping("/create")
 public ResponseEntity<?> createBook(@RequestBody Book book,HttpServletRequest request) {
     
	 String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
	 if(jwtUtils.validateJwtToken(token))	 
     return ResponseEntity.status(HttpStatus.OK).body(bookService.saveBook(book));
	 else
	return	 ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
 }
 
 @PostMapping("/update")
 public ResponseEntity<?> updateBook(@RequestBody Book updatedbook,HttpServletRequest request) {
	 
	 String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
	 if(jwtUtils.validateJwtToken(token))	
	 {
	 Book book = bookService.getBookById(updatedbook.getId()).get();
	 book.setAuthor(updatedbook.getAuthor());
	 book.setDescription(updatedbook.getDescription());
	 book.setTitle(updatedbook.getTitle());
	 book.setPrice(updatedbook.getPrice());
	 return ResponseEntity
                 .ok()
                 .body(bookService.saveBook(book));
	 }
	 else
			return	 ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
     
		 
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<?> deleteBook(@PathVariable Long id,HttpServletRequest request) {
	 String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
	 if(jwtUtils.validateJwtToken(token))	
	 {
	bookService.deleteBook(id);
	return  ResponseEntity
     .ok()
     .body("Book deleted succesfully");
	 }
	 else
	return	 ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
 }
}

