package seal.UserService.Filter;

import java.io.UnsupportedEncodingException;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import seal.UserService.Exceptions.BadRequestException;
import seal.UserService.User.User;
import static seal.UserService.Filter.GlobalValue.EXPIRATION_TIME;
import static seal.UserService.Filter.GlobalValue.secretKey;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static java.util.Collections.emptyList;
import java.util.HashMap;

@Service
public class TokenAuthenticationService {

    public String createTokenUser(User user) {
        Date now = new Date();
        HashMap<String, Object> userJson = new HashMap<>();
        userJson.put("userId", user.getId());
        userJson.put("userImg", user.getImage());
        userJson.put("userName", user.getFirstname());

        String token = Jwts.builder()
                .claim("user", userJson)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                //.signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
        return token;
    }

    public static void validateJWTAuthentication(HttpServletRequest request) {
        System.out.println(secretKey);
        String token = request.getHeader("Authorization");
        try {
            if (token != null) {
                try {
                    String user = Jwts.parser()
                            .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes("UTF-8")))
                            .parseClaimsJws(token.replace("Bearer ", ""))
                            .getBody()
                            .getSubject();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(TokenAuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                throw new BadRequestException("Authorization Header is not found !");
            }
        } catch (io.jsonwebtoken.SignatureException signatureException) {
            throw new BadRequestException("JWT Token has been change we dont trust your token !");
        } catch (io.jsonwebtoken.ExpiredJwtException signExpiredJwtException) {
            throw new BadRequestException("JWT Token Is Already Timeout Please Login Again !");
        }
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();

            return user != null
                    ? new UsernamePasswordAuthenticationToken(user, null, emptyList())
                    : null;
        }
        return null;
    }

}
