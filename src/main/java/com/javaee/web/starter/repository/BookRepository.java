package com.javaee.web.starter.repository;

import com.javaee.web.starter.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

  Book findByIsbn(String isbn);

  List<Book> findAllByIsbnAndName(@Param("isbn") String isbn, @Param("name") String name);
}
