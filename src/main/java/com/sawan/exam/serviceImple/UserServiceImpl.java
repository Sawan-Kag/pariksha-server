package com.sawan.exam.serviceImple;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sawan.exam.exceptions.RoleNoteFound;
import com.sawan.exam.exceptions.UserAlreadyExistsException;
import com.sawan.exam.models.ERole;
import com.sawan.exam.models.Role;
import com.sawan.exam.models.User;
import com.sawan.exam.repository.RoleRepository;
import com.sawan.exam.repository.UserRepository;
import com.sawan.exam.serviceInterface.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
   
   
    @Override
    public User registerUser(User user) throws RoleNoteFound, UserAlreadyExistsException { 
        Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);     
        Set<Role> roles = new HashSet<>();
        
        if (role.isPresent()) {
            roles.add(role.get());
            user.setRoles(roles);
        } else {
            throw new RoleNoteFound("Role not found");
        }
        
        // Checking for pre-existing user
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Username '" + existingUser.get().getUsername() + "' already exists");
        }
        User newUser = userRepository.insert(user);
        return newUser;
    }

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
    @Override
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    user.setEmail(updatedUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow();
    }

    @Override
    public void deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
           
        }
    }


}

