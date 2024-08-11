package com.org.mybookstore.api.user;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.mybookstore.api.util.JwtUtil;
import com.org.mybookstore.exceptions.NoTokenFoundException;
import com.org.mybookstore.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class UserController {



 @Autowired
 private UserService userService;

 @Autowired
 private JwtUtil jwtUtil;

 @PostMapping("/register")
 public ResponseEntity<?> registerUser(@RequestBody User user) {
	 if (userService.findByUsername(user.getUsername()).isPresent()) {
         return ResponseEntity
                 .badRequest()
                 .body("Error: Username is already taken!");
     }
     return ResponseEntity.ok().body(userService.registerUser(user));
 }
 
 @PostMapping("/updateProfile")
 public ResponseEntity<?>  updateUser(HttpServletRequest request,@RequestBody User updateduser) {
	
     
	 	String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
		if (jwtUtil.validateJwtToken(token)) {
			String username = jwtUtil.extractUsername(token);
			User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
			user.setEmail(updateduser.getEmail());
			user.setPassword(updateduser.getPassword());
			return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(user));
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
 }

 @PostMapping("/login")
 public ResponseEntity<?> loginUser(@RequestBody User loginuser) throws Exception {
  

	 User user = userService.findByUsername(loginuser.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
     if(!loginuser.getPassword().equals(user.getPassword()))
     {
    	 return ResponseEntity
                 .status(HttpStatus.UNAUTHORIZED)
                 .body("Bad Credentials");
    	 
     }
     final String jwt = jwtUtil.generateToken(user);

     Map<String, String> response = new HashMap<>();
     response.put("token", jwt);
     return ResponseEntity.ok().body(response);
 }
 
 @GetMapping("/getUser")
 public ResponseEntity<?> getUser(HttpServletRequest request) throws Exception {
  

	 String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
		if (jwtUtil.validateJwtToken(token)) {
			String username = jwtUtil.extractUsername(token);
			User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
 }
 
 
}

