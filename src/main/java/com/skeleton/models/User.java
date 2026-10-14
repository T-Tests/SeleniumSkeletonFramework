package com.skeleton.models;

/** Immutable data holder (POJO) for credentials. ENCAPSULATION: final fields, getters only. */
public class User {

    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "'}"; // never print the password
    }
}