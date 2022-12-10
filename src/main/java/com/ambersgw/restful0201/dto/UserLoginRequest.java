package com.ambersgw.restful0201.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserLoginRequest {
    @NotBlank //4-13
    @Email //當前端傳回email格式，才能通過驗證
    private String email;
    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
