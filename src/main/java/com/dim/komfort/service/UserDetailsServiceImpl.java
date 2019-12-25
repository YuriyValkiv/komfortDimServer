package com.dim.komfort.service;

import com.dim.komfort.domain.MyUserDetails;
import com.dim.komfort.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  @Autowired
  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> optionalUser =userService.getByUserName(username);
    return Optional.ofNullable(optionalUser).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"))
            .map(MyUserDetails::new).get();
  }
}
