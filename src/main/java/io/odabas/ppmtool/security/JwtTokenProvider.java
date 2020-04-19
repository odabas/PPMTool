package io.odabas.ppmtool.security;

import io.jsonwebtoken.*;
import io.odabas.ppmtool.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.xml.transform.sax.SAXSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.odabas.ppmtool.security.SecurityConstants.EXPRATION_TIME;
import static io.odabas.ppmtool.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {
    //Generate the token

    public String  generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date exprireDate = new Date(now.getTime() + EXPRATION_TIME);

        String userId = Long.toString(user.getId());
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",(Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullname",user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exprireDate)
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();

    }


    //Validate token

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch(SignatureException signatureEx){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException malformedEx){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException expiredEx){
            System.out.println("Expired JWT Exception");
        }catch (UnsupportedJwtException unsupportedEx){
            System.out.println("JWT claims string is empty");
        }

        return false;
    }

    //Get user id from token
    public Long getUserIdFromJWT(String token)  {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }
}
