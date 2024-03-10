package com.tareq.springboot.security;

import javax.validation.constraints.NotEmpty;

public class SignUpRequest extends SignInRequest{
    @NotEmpty
    private String name;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, String password, String name) {
        super(username, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
