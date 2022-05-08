package com.javaee.web.starter.config;

import com.javaee.web.starter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class Config extends WebSecurityConfigurerAdapter {

  private UserService userService;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .authorizeRequests()
        .antMatchers("/sign-up").not().fullyAuthenticated()
        .antMatchers("/liked-books", "/like/**", "/dislike").hasAnyRole("USER", "ADMIN")
        .antMatchers("/book").hasRole("ADMIN")
        .antMatchers("/", "/book/**").permitAll()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .and()
        .formLogin()
        .loginPage("/sign-in")
        .permitAll();

    httpSecurity.csrf().disable();
    httpSecurity.headers().frameOptions().disable();
  }

  @Autowired
  protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
  }
}
