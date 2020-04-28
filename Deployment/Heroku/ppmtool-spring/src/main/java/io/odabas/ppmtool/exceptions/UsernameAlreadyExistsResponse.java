package io.odabas.ppmtool.exceptions;


import lombok.Getter;
import lombok.Setter;


public class UsernameAlreadyExistsResponse {

    @Getter
    @Setter
    private String  username;

    public UsernameAlreadyExistsResponse(String username) {
            this.username = username;
    }
}
