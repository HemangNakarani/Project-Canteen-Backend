package io.sen.canteenia.payload.request;

import javax.validation.constraints.NotBlank;

public class ResetPasswordRequest {

    @NotBlank
    String plainUserPassword;

    @NotBlank
    String email;

    @NotBlank
    String token;

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "plainUserPassword='" + plainUserPassword + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getPlainUserPassword() {
        return plainUserPassword;
    }

    public void setPlainUserPassword(String plainUserPassword) {
        this.plainUserPassword = plainUserPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ResetPasswordRequest(@NotBlank String plainUserPassword, @NotBlank String email, @NotBlank String token) {
        this.plainUserPassword = plainUserPassword;
        this.email = email;
        this.token = token;
    }
}
