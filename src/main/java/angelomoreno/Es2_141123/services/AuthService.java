package angelomoreno.Es2_141123.services;

import angelomoreno.Es2_141123.entities.Utente;
import angelomoreno.Es2_141123.exceptions.UnauthorizedException;
import angelomoreno.Es2_141123.payloads.entities.UtenteLoginDTO;
import angelomoreno.Es2_141123.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;

    public String authenticateUtente(UtenteLoginDTO body) {
        Utente utente = utenteService.findByEmail(body.email());
        if (body.password().equals(utente.getPassword())) {
            return jwtTools.createToken(utente);
        } else {
            throw new UnauthorizedException("Le credenziali non sono valide. Riprova");
        }
    }
}
