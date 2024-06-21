package com.sawan.exam.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sawan.exam.dto.request.LoginRequest;
import com.sawan.exam.dto.request.SignupRequest;
import com.sawan.exam.dto.response.ApiResponse;
import com.sawan.exam.dto.response.MessageResponse;
import com.sawan.exam.dto.response.UserInfoResponse;
import com.sawan.exam.exceptions.TokenRefreshException;
import com.sawan.exam.jwt.JwtUtils;
import com.sawan.exam.models.ERole;
import com.sawan.exam.models.RefreshToken;
import com.sawan.exam.models.Role;
import com.sawan.exam.models.User;
import com.sawan.exam.repository.RoleRepository;
import com.sawan.exam.repository.UserRepository;
import com.sawan.exam.serviceImple.RefreshTokenService;
import com.sawan.exam.serviceImple.RoleServiceImple;
import com.sawan.exam.serviceImple.UserDetailsImpl;
import com.sawan.exam.serviceImple.UserDetailsServiceImpl;



//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;
  
  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;
  
  @Autowired
  UserDetailsServiceImpl detailsServiceImpl;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
   
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    


    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    
    ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());
    
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
        

    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(),
                                       userDetails.getUsername(),
                                       userDetails.getEmail(),
                                       userDetails.getfName(),
                                       userDetails.getlName(),
                                       roles,
                                       userDetails.isEnabled()));
  } 

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
    }
 
    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         encoder.encode(signUpRequest.getPassword())
                         ,signUpRequest.getEmail(),signUpRequest.getfName(),signUpRequest.getlName());

    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {

      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    User newUser = userRepository.save(user);
    return ResponseEntity.ok(new ApiResponse<User>(true,"User registered successfully!",newUser));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principle.toString() != "anonymousUser") {      
      String userId = ((UserDetailsImpl) principle).getId();
      refreshTokenService.deleteByUserId(userId);
    }
    
    ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
    ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
        .body(new ApiResponse<Object>(false,"You've been signed out!",principle));
  }
 
  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
      String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);    
      if ((refreshToken != null) && (refreshToken.length() > 0)) {
          Optional<User> userOptional = refreshTokenService.findByToken(refreshToken)
              .map(refreshTokenService::verifyExpiration)
              .map(RefreshToken::getUser);

          if (userOptional.isPresent()) {	
              User user = userOptional.get();
              ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
              return ResponseEntity.ok()
                  .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                  .body(new MessageResponse("Token is refreshed successfully!"));
          } else {
              throw new TokenRefreshException(refreshToken, "Refresh token is not in database!");
          }
      }

      return ResponseEntity.badRequest().body(new ApiResponse<String>(false, "You've been signed out!", refreshToken));
  }
  
  @GetMapping("/current-user")
  public UserDetails getCurrentUser(Principal principal) { 
      return this.detailsServiceImpl.loadUserByUsername(principal.getName());

  }

}
