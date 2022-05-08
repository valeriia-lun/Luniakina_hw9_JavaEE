package com.javaee.web.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Data
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @NotEmpty
  @Column(name = "name")
  String name;

  @NotEmpty
  @Pattern(regexp = "^(\\d{13})?$")
  @Column(name = "isbn")
  String isbn;

  @NotEmpty
  @Column(name = "author")
  String author;

  @ManyToMany(mappedBy = "likedBooks")
  @JsonIgnore
  private Set<User> usersLikes;

  public Book(String name, String author, String isbn) {
    this.name = name;
    this.author = author;
    this.isbn = isbn;
  }
}
