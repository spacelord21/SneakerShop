package ru.spacelord.sneakershop.sneakershop.config.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.spacelord.sneakershop.sneakershop.services.UserDetailsImpl;

import java.util.Date;

@Component
@PropertySource("classpath:jwt.properties")
public class JwtUtils {

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    private final String jwtExpirationMs;

    {
        assert false;
        jwtExpirationMs = environment.getProperty("jwtExpirationMs");
    }

    private final String jwtSecret;

    {
        assert false;
        jwtSecret = environment.getProperty("jwtSecret");
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | SignatureException e) {
            System.out.println("Invalid JWT signature: "+  e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: "+  e.getMessage());
        }catch (UnsupportedJwtException e) {
            System.out.println("JWT token unsupported: "+  e.getMessage());
        }catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: "+  e.getMessage());
        }
        return false;
    }

}
