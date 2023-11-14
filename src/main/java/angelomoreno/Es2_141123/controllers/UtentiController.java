package angelomoreno.Es2_141123.controllers;

import angelomoreno.Es2_141123.entities.Utente;
import angelomoreno.Es2_141123.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("")
    public Page<Utente> getUtenti (@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "utenteId") String id
                                   ) {
        return utenteService.getUtenti(page, size, id);
    }

    @GetMapping("/{id}")
    public Utente findById(@PathVariable int id) {
        return utenteService.findById(id);
    }

    @PutMapping("/{id}")
    public Utente modificaUtente(@PathVariable int id, @RequestBody Utente body) {
        return utenteService.modificaUtente(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaUtente(@PathVariable int id) {
        utenteService.eliminaUtente(id);
    }

    @PostMapping("/upload")
    public String uploadFiles(@RequestParam("imgProfile")MultipartFile body) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return utenteService.uploadPicture(body);
    }
}
