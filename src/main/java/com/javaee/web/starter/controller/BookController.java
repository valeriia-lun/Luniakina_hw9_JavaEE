package com.javaee.web.starter.controller;

import com.javaee.web.starter.model.Book;
import com.javaee.web.starter.model.User;
import com.javaee.web.starter.service.BookService;
import com.javaee.web.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

  private final BookService booksService;
  private final UserService userService;

  @PostMapping
  public ResponseEntity<List<Book>> saveBook(@RequestBody final Book book) {
    booksService.saveBook(book);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(booksService.getAllBooks());
  }

  @PostMapping(value = "/filter")
  public ResponseEntity<List<Book>> findBooks(@RequestBody final Book book) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(booksService.getBooksByNameAndIsbn(book.getName(), book.getIsbn()));
  }

  @GetMapping
  public ResponseEntity<List<Book>> getAll() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(booksService.getAllBooks());
  }

  @RequestMapping(value = "/book/{isbn}", method = RequestMethod.GET)
  public String getBook(@PathVariable String isbn, Model model) {
    Book book = booksService.getBookByIsbn(isbn);
    model.addAttribute("book", book);
    return "book";
  }

  @GetMapping("/like/{isbn}")
  public String likeBook(@PathVariable String isbn, @AuthenticationPrincipal UserDetails currentUser) {
    Optional<User> user = userService.findUserByUsername(currentUser.getUsername());
    userService.likeBook(user, booksService.getBookByIsbn(isbn));
    return "redirect:/liked-books";
  }

  @GetMapping("/liked-books")
  public String getLikedBooks(Model model, @AuthenticationPrincipal UserDetails currentUser) {
    Optional<User> user = userService.findUserByUsername(currentUser.getUsername());
    model.addAttribute("books", user.get().getLikedBooks());
    return "liked-books";
  }

  @PostMapping("/dislike")
  public String dislikeBook(@RequestParam(name = "isbn") String isbn, @AuthenticationPrincipal UserDetails currentUser) {
    Optional<User> user = userService.findUserByUsername(currentUser.getUsername());
    userService.dislikeBook(user, booksService.getBookByIsbn(isbn));
    return "redirect:/liked-books";
  }
}
