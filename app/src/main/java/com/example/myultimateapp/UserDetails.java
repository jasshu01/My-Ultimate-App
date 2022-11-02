package com.example.myultimateapp;

public class UserDetails {

    private String title, firstName, lastName;
    private String dob, gender;
    private String username, password;
    private String email, phone;
    private String imageurls;
    private String address, postalcode;
    private String securityquestion, securityanswer;

    public UserDetails() {
    }

    public UserDetails(String title, String firstName, String lastName, String dob, String gender, String username, String password, String email, String phone, String imageurls, String address, String postalcode, String securityquestion, String securityanswer) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.imageurls = imageurls;
        this.address = address;
        this.postalcode = postalcode;
        this.securityquestion = securityquestion;
        this.securityanswer = securityanswer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageurls() {
        return imageurls;
    }

    public void setImageurls(String imageurls) {
        this.imageurls = imageurls;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getSecurityquestion() {
        return securityquestion;
    }

    public void setSecurityquestion(String securityquestion) {
        this.securityquestion = securityquestion;
    }

    public String getSecurityanswer() {
        return securityanswer;
    }

    public void setSecurityanswer(String securityanswer) {
        this.securityanswer = securityanswer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
