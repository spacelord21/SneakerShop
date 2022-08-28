package ru.spacelord.sneakershop.sneakershop.config;

public class AuthenticationBean {
    private String message;

    public AuthenticationBean(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
