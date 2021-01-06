package com.vapitea.datacollector.configuration;

import com.vapitea.datacollector.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JpaUserDetailsService jpaUserDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //Enabling http basic auth
    http.httpBasic();

    //Disabling csrf token
     http.csrf().disable();

    //This line disables JSESSIONID cookies, cookies should be disabled until Csrf vulnerability is dealt with
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    //Allowing free access to h2 console, should not be used in production!
    http
      .headers()
      .frameOptions()
      .sameOrigin()
      .and()
      .authorizeRequests().antMatchers("/h2/**").permitAll();


    //Allowing access for anyone to post measurements if they know DataSource UUID
    http.authorizeRequests().antMatchers("/api/v1.0/measurements").permitAll();

    //Securing rest api
    http.authorizeRequests().antMatchers("/api/**").authenticated();


    //allowing access to the rest of the resources to redirect to single page application, might be a bad idea
    http.authorizeRequests().anyRequest().permitAll();

  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
