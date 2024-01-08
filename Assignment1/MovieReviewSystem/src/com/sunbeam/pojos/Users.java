package com.sunbeam.pojos;

import java.util.Date;

public class Users {
    int id;
    String firstName;
    String lastName;
    String email;
    String passwd;
    String mobile;
    Date birth;

    public Users() {
    }

    public Users(int id, String firstNname, String lastName, String email, String passwd, String mobile, Date birth) {
        this.id = id;
        this.firstName = firstNname;
        this.lastName = lastName;
        this.email = email;
        this.passwd = passwd;
        this.mobile = mobile;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstNname) {
        this.firstName = firstNname;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstNname='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", mobile='" + mobile + '\'' +
                ", birth=" + birth +
                '}';
    }
}