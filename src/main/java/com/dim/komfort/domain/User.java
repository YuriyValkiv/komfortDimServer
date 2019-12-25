package com.dim.komfort.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "USERS")
public class User implements Serializable {

  public User(User user) {
    id = user.getId();
    password = user.getPassword();
    userName = user.getUserName();
    roles = user.getRoles();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id", unique = true)
  private Long id;

  @Column(name = "password")
  private String password;

  @Column(name = "name")
  private String userName;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roles;
}
