package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.EntityNotFoundException;
import pl.coderslab.servicestation.models.Status;
import pl.coderslab.servicestation.repositories.StatusRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<Status> statusList() {
        return statusRepository.findAll();
    }

    public Status findById(Long id) {
        return statusRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }
}
