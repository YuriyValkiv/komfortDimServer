package com.dim.komfort.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "ROLES")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id", unique = true)
  private int id;

  @Column(name = "role")
  private String role;
}
