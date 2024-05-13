package org.valerochka1337.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.valerochka1337.dto.AuthDTO;
import org.valerochka1337.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;

  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthDTO authRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authRequest.getUsername(), authRequest.getPassword()));
      userRepository
          .findByUsername(authRequest.getUsername())
          .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

      Map<Object, Object> response = new HashMap<>();
      response.put("username", authRequest.getUsername());
      response.put("message", "Successfully login");

      return ResponseEntity.ok(response);
    } catch (AuthenticationException exception) {
      return new ResponseEntity<>("Invalid email/password", HttpStatus.FORBIDDEN);
    }
  }

  @DeleteMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    logoutHandler.logout(request, response, null);
  }
}
