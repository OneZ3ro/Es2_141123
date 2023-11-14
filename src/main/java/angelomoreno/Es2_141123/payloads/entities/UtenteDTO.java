package angelomoreno.Es2_141123.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UtenteDTO(
        @NotEmpty(message = "L'username è obbligatorio")
        @Size(min = 5, max = 15, message = "L'username deve avere tra i 5 e 15 caratteri")
        String username,
        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(min = 3, max = 20, message = "Il nome deve avere tra i 3 e 20 caratteri")
        String nome,
        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 5, max = 30, message = "Il cognome deve avere tra i 5 e 30 caratteri")
        String cognome,
        @NotEmpty(message = "L'emmail è obbligatorio")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        String password
//        String urlImg
) {}
