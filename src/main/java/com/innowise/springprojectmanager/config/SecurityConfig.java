package com.innowise.springprojectmanager.config;

import com.innowise.springprojectmanager.model.enumeration.Role;
import com.innowise.springprojectmanager.security.jwt.filter.JwtAuthenticationFilter;
import com.innowise.springprojectmanager.utils.literal.PropertySourceClasspath;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Configuration
@EnableWebSecurity
@PropertySource({
  PropertySourceClasspath.SECURITY_PROPERTIES_CLASSPATH,
  PropertySourceClasspath.JWT_PROPERTIES_CLASSPATH
})
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${encrypt.rounds}")
  private int encryptRounds;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/auth/*")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/auth/email-confirmation")
        .hasAuthority(Role.ADMIN.name())
        .antMatchers("/api/users/*")
        .hasAuthority(Role.ADMIN.name())
        .antMatchers("/api/projects/*")
        .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
        .antMatchers("/api/projects")
        .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
        .antMatchers("/api/tasks/*")
        .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
        .antMatchers("/api/tasks")
        .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
        .antMatchers("/*")
        .permitAll()
        .anyRequest()
        .permitAll();

    http.addFilterBefore(
        authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  protected PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(encryptRounds);
  }

  /**
   * Returns authenticationManagerBean() and adds this one to Application context.
   *
   * @return authenticationManagerBean
   */
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * Returns authenticationTokenFilterBean() and adds this one to Application context.
   *
   * @return JwtAuthenticationFilterBean
   */
  @Bean
  public JwtAuthenticationFilter authenticationTokenFilterBean() {
    return new JwtAuthenticationFilter();
  }
}
