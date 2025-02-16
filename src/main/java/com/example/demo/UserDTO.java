package com.example.demo;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

// Data Transfer Object for LDAP user
public class UserDTO{

    UserDTO(String email, String commonName, String surname, String password){
        this.commonName = commonName;
        this.email = email;
        this.surname = surname;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }
    public String getCommonName() {
        return this.commonName;
    }
    public String getSurname() {
        return this.surname;
    }
    public String getPassword() {
        return this.password;
    }
    
    private String email;
    private String commonName;
    private String surname;
    private String password;

    public Attributes toAttributes(){
        Attributes attributes = new BasicAttributes();
        attributes.put("objectClass","inetOrgPerson");
        attributes.put("cn",commonName);
        attributes.put("sn",surname);
        attributes.put("userPassword",password);
        return attributes;
    }
}
