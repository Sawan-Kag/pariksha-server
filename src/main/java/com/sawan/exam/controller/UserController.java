package com.sawan.exam.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sawan.exam.models.User;
import com.sawan.exam.serviceInterface.UserService;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "https://pariksha-server-production.up.railway.app")
public class UserController {

 @Autowired
 private UserService userService;

 @GetMapping("/all")
// @PreAuthorize("hasRole('ADMIN')")
 public List<User> getAllUsers() {
     return  userService.getAllUsers();
 }
 
 @GetMapping("/{userId}")
 @PreAuthorize("hasRole('ADMIN')")
 public ResponseEntity<User> getUserById(@PathVariable String userId) {
	 System.out.println(userId);
     Optional<User> user = userService.getUserById(userId);
     return user.map(ResponseEntity::ok)
             .orElse(ResponseEntity.notFound().build());
 }

 @PutMapping("/update/{userId}")
 @PreAuthorize("hasRole('ROLE_ADMIN')")
 public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
     try {
         User updated = userService.updateUser(userId, updatedUser);
         return ResponseEntity.ok(updated);
     } catch (Exception e) {
         return ResponseEntity.notFound().build();
     }
 }

 @DeleteMapping("/delete/{userId}")
 @PreAuthorize("hasRole('ADMIN')")
 public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
     try {
         userService.deleteUser(userId);
         return ResponseEntity.noContent().build();
     } catch (Exception e) {
         return ResponseEntity.notFound().build();
     }
 }
 
}
