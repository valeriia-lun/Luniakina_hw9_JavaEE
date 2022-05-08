package com.javaee.web.starter.service;

import com.javaee.web.starter.model.Book;
import com.javaee.web.starter.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;

  @EventListener
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    bookRepository.save(Book.builder().id(1).isbn("123").name("The flower").author("John White").build());
    bookRepository.save(Book.builder().id(2).isbn("456").name("The sun").author("Ann Snow").build());
    bookRepository.save(Book.builder().id(3).isbn("789").name("The moon").author("Berry Whill").build());
  }

  @Transactional
  public void saveBook(Book book) {
    if (isNull(bookRepository.findByIsbn(book.getIsbn()))) {
      bookRepository.save(book);
    }
    log.info("A new was added {}", book);
  }

  @Transactional
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @Transactional
  public List<Book> getBooksByNameAndIsbn(String name, String isbn) {
    return bookRepository.findAllByIsbnAndName(isbn, name);
  }

  @Transactional
  public Book getBookByIsbn(String isbn){
    return bookRepository.findByIsbn(isbn);
  }
}
