package angelomoreno.Es2_141123.controllers;

import angelomoreno.Es2_141123.entities.Utente;
import angelomoreno.Es2_141123.exceptions.BadRequestException;
import angelomoreno.Es2_141123.payloads.entities.UtenteDTO;
import angelomoreno.Es2_141123.payloads.entities.UtenteLoginDTO;
import angelomoreno.Es2_141123.payloads.entities.UtenteLoginSuccessDTO;
import angelomoreno.Es2_141123.services.AuthService;
import angelomoreno.Es2_141123.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public UtenteLoginSuccessDTO login(@RequestBody UtenteLoginDTO body) {
        return new UtenteLoginSuccessDTO(authService.authenticateUtente(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente saveUtente(@RequestBody @Validated UtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return utenteService.saveUtente(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
