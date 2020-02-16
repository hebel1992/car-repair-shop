package pl.coderslab.servicestation;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id) {
        super("Could not find entity with id: " + id);
    }
}
