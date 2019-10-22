package com.daca.listapramim.api.security;

import com.daca.listapramim.api.user.Payload;
import com.daca.listapramim.api.user.Privilege;
import com.daca.listapramim.api.user.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Service
public class TokenAuthenticationService {

    public static final long EXPIRATION_TIME = 21_600_000L;
    private static String SECRET = "raj9s0e9c0r9e0t";
    private static String SECRET_KEY = Base64.getEncoder().encodeToString(SECRET.getBytes());
    private static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER = "Authorization";

    private static Gson gson = new GsonBuilder().create();

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            Payload userModel = gson.fromJson(user, Payload.class);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user,
                        null,
                        userModel.getAuthorities());
            }
        }
        return null;
    }

    static void addAuthentication(HttpServletResponse response, String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        response.addHeader(HEADER, TOKEN_PREFIX + " " + JWT);

    }

    public final String generateToken(final Payload user) {
        return Jwts.builder()
                .setSubject(gson.toJson(user))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
