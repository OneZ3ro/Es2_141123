package angelomoreno.Es2_141123.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(int id) {
        super("L'elemento con id: " + id + " non è stato trovato. Riprovare con un id diverso");
    }

    public NotFoundException(String email) {
        super("L'utente con email: " + email + " non è stato trovato. Riprovare con una mail diversa");
    }
}
