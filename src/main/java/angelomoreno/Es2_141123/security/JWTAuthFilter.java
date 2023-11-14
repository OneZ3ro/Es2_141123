package angelomoreno.Es2_141123.security;

import angelomoreno.Es2_141123.entities.Utente;
import angelomoreno.Es2_141123.exceptions.UnauthorizedException;
import angelomoreno.Es2_141123.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UtenteService utenteService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Devi passare un Bearer Token nell'Authorization Header");
        } else {
            String token = authHeader.substring(7);
            System.out.println("Token: " + token);
            jwtTools.verifyToken(token); //verifico che il token non sia stato manomesso

            String id = jwtTools.extractIdFromToken(token);
            Utente currentUtente = utenteService.findById(Integer.parseInt(id)); //trovo l'utente con l'id estratto

            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUtente, null);
            SecurityContextHolder.getContext().setAuthentication(authentication); // dico a spring security che l'utente ha il permesso di proseguire. Se non lo metto mi tornerebbe error 403

            filterChain.doFilter(request, response); // con questo pezzo andiamo al prossimo filter
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Questo metodo serve per specificare quando il filtro JWTAuthFilter non debba entrare in azione
        // Ad es tutte le richieste al controller /auth/** non devono essere filtrate
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
