package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
        .authorizeHttpRequests((authorize) -> authorize
          .anyRequest().fullyAuthenticated() // All requests to all endpoints need authentication
        )
        .formLogin(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable); // Disable CSRF protection
  
      return http.build();
    }

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl("ldap://localhost:10389"); // URL of our LDAP server
        ldapContextSource.setUserDn("uid=admin,ou=system"); // Distinguishable Node of Admin
        ldapContextSource.setPassword("secret"); // Password of Admin
        return ldapContextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource()); // Template used for creating new users
    }

    @Bean
    AuthenticationManager authenticationManager(BaseLdapPathContextSource source){
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(source);
        factory.setUserDnPatterns("userid={0},ou=users,ou=system"); // Location of our user directory
        return factory.createAuthenticationManager();
    }
}