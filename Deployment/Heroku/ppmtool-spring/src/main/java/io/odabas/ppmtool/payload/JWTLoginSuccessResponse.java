package io.odabas.ppmtool.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class JWTLoginSuccessResponse {
    private boolean success;
    private String token;




    @Override
    public String toString() {
        return "JWTLoginSuccessResponse{" +
                "success=" + success +
                ", token=" + token +
                '}';
    }
}
