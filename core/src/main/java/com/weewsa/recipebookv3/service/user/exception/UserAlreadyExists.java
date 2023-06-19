package com.weewsa.recipebookv3.service.user.exception;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String message) {
        super(message);
    }
}
