package com.dim.komfort.service;

import com.dim.komfort.domain.User;
import com.dim.komfort.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> getById(Long id) {
    return Optional.ofNullable(userRepository.getOne(id));
  }

  public Optional<User> getByUserName(String userName) {
    return Optional.ofNullable(userRepository.findUserByUserName(userName));
  }
}
