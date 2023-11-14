package angelomoreno.Es2_141123.services;

import angelomoreno.Es2_141123.entities.Utente;
import angelomoreno.Es2_141123.enums.Role;
import angelomoreno.Es2_141123.exceptions.BadRequestException;
import angelomoreno.Es2_141123.exceptions.NotFoundException;
import angelomoreno.Es2_141123.payloads.entities.UtenteDTO;
import angelomoreno.Es2_141123.repositories.UtenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Page<Utente> getUtenti (int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return utenteRepository.findAll(pageable);
    }



    public Utente findById(int id) throws NotFoundException {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public Utente modificaUtente(int id, Utente body) throws NotFoundException {
         Utente utente = this.findById(id);
         utente.setUsername(body.getUsername());
         utente.setNome(body.getNome());
         utente.setCognome(body.getCognome());
         utente.setEmail(body.getEmail());
//         utente.setPassword(bcrypt.encode(body.getPassword()));
         utente.setRole(Role.UTENTE);
         utente.setUrlImg("https://ui-avatars.com/api/?name=" + body.getNome() + "+" + body.getCognome());
         return utenteRepository.save(utente);
    }

    public void eliminaUtente(int id) throws NotFoundException {
        utenteRepository.deleteById(id);
    }

    public String uploadPicture(MultipartFile file) throws IOException {
        return (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }
}
