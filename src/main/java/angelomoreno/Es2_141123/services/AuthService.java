package angelomoreno.Es2_141123.services;

import angelomoreno.Es2_141123.entities.Utente;
import angelomoreno.Es2_141123.enums.Role;
import angelomoreno.Es2_141123.exceptions.BadRequestException;
import angelomoreno.Es2_141123.exceptions.UnauthorizedException;
import angelomoreno.Es2_141123.payloads.entities.UtenteDTO;
import angelomoreno.Es2_141123.payloads.entities.UtenteLoginDTO;
import angelomoreno.Es2_141123.repositories.UtenteRepository;
import angelomoreno.Es2_141123.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUtente(UtenteLoginDTO body) {
        Utente utente = utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), utente.getPassword())) {
            return jwtTools.createToken(utente);
        } else {
            throw new UnauthorizedException("Le credenziali non sono valide. Riprova");
        }
    }

    public Utente saveUtente(UtenteDTO body) throws IOException {
        utenteRepository.findByEmail(body.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email " + utente.getEmail() + " è già stata utilizzata!");
        });

        Utente utente = new Utente();
        utente.setUsername(body.username());
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setEmail(body.email());
        utente.setPassword(bcrypt.encode(body.password()));
        utente.setRole(Role.UTENTE);
        utente.setUrlImg("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return utenteRepository.save(utente);
    }
}
