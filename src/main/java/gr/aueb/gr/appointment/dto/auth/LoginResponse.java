package gr.aueb.gr.appointment.dto.auth;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
