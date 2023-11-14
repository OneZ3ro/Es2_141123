package angelomoreno.Es2_141123.controllers;

import angelomoreno.Es2_141123.entities.Dispositivo;
import angelomoreno.Es2_141123.exceptions.BadRequestException;
import angelomoreno.Es2_141123.payloads.entities.DispositivoDTO;
import angelomoreno.Es2_141123.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispositivi")
public class DispositiviController {
    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping("")
    public Page<Dispositivo> getDispositivi (@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "dispositivoId") String orderBy) {
        return dispositivoService.getDispositivi(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Dispositivo saveDispositivo(@RequestBody @Validated DispositivoDTO body, BindingResult validation) {
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return dispositivoService.saveDispositivo(body);
        }
    }

    @GetMapping("/{id}")
    public Dispositivo findById(@PathVariable int id) {
        return dispositivoService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dispositivo modificaDispositivo (@PathVariable int id, @RequestBody @Validated DispositivoDTO body) {
        return dispositivoService.modificaDispositivo(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminaDispositivo(@PathVariable int id) {
        dispositivoService.eliminaDispositivo(id);
    }
}
