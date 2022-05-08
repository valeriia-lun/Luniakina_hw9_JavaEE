package com.javaee.web.starter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class Role implements GrantedAuthority {
  @Id
  private Long id;

  private String name;

  @Transient
  @ManyToMany(mappedBy = "roles")
  private Set<User> users;

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String getAuthority() {
    return getName();
  }
}
