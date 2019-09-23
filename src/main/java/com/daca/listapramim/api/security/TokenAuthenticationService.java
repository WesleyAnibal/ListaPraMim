package com.daca.listapramim.api.security;

import com.daca.listapramim.api.user.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;


@Service
public class TokenAuthenticationService {

    public static final long EXPIRATION_TIME = 21_600_000L;
    private static String SECRET = "raj9s0e9c0r9e0t";
    private static String SECRET_KEY = Base64.getEncoder().encodeToString(SECRET.getBytes());
    private static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER = "Authorization";


    static Authentication getAuthentication(HttpServletRequest request){
        String token = request.getHeader(HEADER);
        if(token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }

    static void addAuthentication(HttpServletResponse response, String username){
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        response.addHeader(HEADER, TOKEN_PREFIX+ " "+JWT);

    }

    public final String generateToken(final UserModel user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
