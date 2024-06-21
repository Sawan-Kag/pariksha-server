package com.sawan.exam.serviceInterface;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sawan.exam.exceptions.RoleNoteFound;
import com.sawan.exam.models.User;
@Service
public interface UserService{
    User registerUser(User user) throws RoleNoteFound;
    List<User> getAllUsers();
    Optional<User> getUserById(String userId);
    User updateUser(String userId, User updatedUser);
    void deleteUser(String userId);
   
}
