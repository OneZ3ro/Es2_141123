package angelomoreno.Es2_141123.entities;

import angelomoreno.Es2_141123.enums.StatoDispositivo;
import angelomoreno.Es2_141123.enums.TipoDispositivo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "dispositivi")
@Getter
@Setter
@ToString
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispositivo_id")
    private int dispositivoId;
    @Enumerated(EnumType.STRING)
    private TipoDispositivo tipoDispositivo;
    @Enumerated(EnumType.STRING)
    private StatoDispositivo statoDispositivo;
    @ManyToOne
    @JoinColumn(name = "utenti_id")
    private Utente utente;
}
