package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.models.Vehicle;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        vehicleRepository.delete(vehicle);
    }

    public Vehicle findById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        return vehicle;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

}
