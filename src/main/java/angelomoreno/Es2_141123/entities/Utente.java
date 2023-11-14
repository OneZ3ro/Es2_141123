package angelomoreno.Es2_141123.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "utenti")
@Getter
@Setter
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utenti_id")
    private int utenteId;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    @OneToMany(mappedBy = "utente")
    @JsonIgnore
    private List<Dispositivo> dispositivi;
    @Column(name = "url_img")
    private String urlImg;
}
