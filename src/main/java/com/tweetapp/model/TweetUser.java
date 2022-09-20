package com.tweetapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Document(collection="tweetuser")
public class TweetUser {

    @NotEmpty(message = "firstName is empty")
    private String firstName;

    @NotEmpty(message = "LastName is empty")
    private String lastName;

    @NotEmpty(message = "email is empty")
    @Indexed(unique = true)
    private String email;

    @NotEmpty(message = "loginId is empty")
    @Id
    private String loginId;

    @NotEmpty(message = "password is empty")
    private String password;

    @NotEmpty(message = "confirmPassword is empty")
    private String confirmPassword;

    @NotEmpty(message = "contactNumber is empty")
    private String contactNumber;

    @NotEmpty(message = "role is empty")
    private List<String> roles;

    @NotEmpty(message = "secret key is empty")
    private String secretKey;

    public TweetUser(String firstName, String lastName, String email, String loginId, String password, String confirmPassword, String contactNumber, List<String> roles,String secretKey) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.contactNumber = contactNumber;
        this.roles = roles;
        this.secretKey = secretKey;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public TweetUser() {
    }
}
