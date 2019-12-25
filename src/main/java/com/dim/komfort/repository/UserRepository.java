package com.dim.komfort.repository;

import com.dim.komfort.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByUserName(@Param("userName") String userName);
}
