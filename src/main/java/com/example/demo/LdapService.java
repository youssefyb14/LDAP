package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;
    
  	// Create a entry in the LDAP server
    public void createUser(UserDTO userDTO){
        ldapTemplate.bind("userid="+userDTO.getEmail()+",ou=users,ou=system", null, userDTO.toAttributes());
    }
}