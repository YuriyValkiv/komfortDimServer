package com.dim.komfort.controller;

import com.dim.komfort.config.JwtTokenUtil;
import com.dim.komfort.domain.JwtRequest;
import com.dim.komfort.domain.JwtResponse;
import com.dim.komfort.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class JwtAuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsServiceImpl jwtInMemoryUserDetailsService;

  @Autowired
  public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                     JwtTokenUtil jwtTokenUtil,
                                     UserDetailsServiceImpl jwtInMemoryUserDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
  }

  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
          throws Exception {

    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    final UserDetails userDetails = jwtInMemoryUserDetailsService
            .loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(token));
  }

  private void authenticate(String username, String password) throws Exception {
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}
