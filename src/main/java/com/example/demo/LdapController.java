package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LdapController {

    @Autowired
    LdapService ldapService;
    
    @GetMapping("/")
    @ResponseBody // This is used to override the default behaviour of @Controller Annotation to return HTML pages.
    public String homePage() {
        return "Welcome to home page!"; // This text will be only visible to authenticated users.
    }

    @GetMapping("/add-user")
    public String addUser() {
        return "addUser.html"; // Custom form page to take inputs from users and create them.
    }
  
    // We will be receiving data from the HTML page and create a user object to generate LDAP user using LdapService.
    @PostMapping(path="/add-user", consumes="application/x-www-form-urlencoded") 
    public String generateUser(@RequestParam Map<String, String> body) {
        UserDTO userDTO = new UserDTO(body.get("email"), body.get("commonName"), body.get("surname"), body.get("password"));
        ldapService.createUser(userDTO);
        return "redirect:/add-user";
    }    
}