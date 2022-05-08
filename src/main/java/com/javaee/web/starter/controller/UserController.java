package com.javaee.web.starter.controller;

import com.javaee.web.starter.model.User;
import com.javaee.web.starter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/sign-in")
  public String signInPage(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("error", null);
    return "login";
  }

  @PostMapping("/sign-in")
  public String signIn(@ModelAttribute User user) {
    try {
      userService.login(user.getUsername(), user.getPassword());
      return "redirect:/";
    } catch (Exception e) {
      return "login";
    }
  }

  @GetMapping("/sign-up")
  public String signUpPage(Model model) {
    model.addAttribute("user", new User());
    return "signup";
  }

  @PostMapping("/sign-up")
  public String signUp(@Valid @ModelAttribute User user) {
    userService.signup(user);
    try {
      return "redirect:/sign-in";
    } catch (Exception e) {
      return "signup";
    }
  }

}
