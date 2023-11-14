package angelomoreno.Es2_141123.payloads.entities;

import angelomoreno.Es2_141123.enums.StatoDispositivo;
import angelomoreno.Es2_141123.enums.TipoDispositivo;
import jakarta.validation.constraints.NotNull;

public record DispositivoDTO(
        @NotNull(message = "Lo stato del dispositivo è obbligatorio")
        TipoDispositivo tipoDispositivo,
        @NotNull(message = "Lo stato del dispositivo è obbligatorio")
        StatoDispositivo statoDispositivo,

        int utenteId
) {
}
