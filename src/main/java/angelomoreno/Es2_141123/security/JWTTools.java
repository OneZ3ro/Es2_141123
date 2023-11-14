package angelomoreno.Es2_141123.security;

import angelomoreno.Es2_141123.entities.Utente;
import angelomoreno.Es2_141123.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String createToken(Utente utente) {
        return Jwts.builder().setSubject(String.valueOf(utente.getUtenteId())) // subject sarebbe colui a cui appartiene il token
                .setIssuedAt(new Date(System.currentTimeMillis())) // la data di emissione del token (IAT: Issued AT)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7 )) // data di scadenza del token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();  // serve per chiudere questo mega builder
    }

    public void verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token); // mi controlla il token. Se non va bene manda una eccezione in base all'errore
        } catch (Exception exception) {
            throw new UnauthorizedException("Il token non è valido. Fai di nuovo il login per un nuovo token");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody().getSubject();
    }
}
