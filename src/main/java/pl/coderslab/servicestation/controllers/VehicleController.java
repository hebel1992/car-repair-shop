package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.models.FuelType;
import pl.coderslab.servicestation.models.Vehicle;
import pl.coderslab.servicestation.repositories.CustomerRepository;
import pl.coderslab.servicestation.repositories.VehicleRepository;

import javax.validation.Valid;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @GetMapping
    public String allVehicles() {
        return "vehicles/vehiclesList";
    }

    @GetMapping("/add")
    public String addVehicle(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);
        return "vehicles/addVehicle";
    }

    @PostMapping("/add-action")
    public String addVehicle(@ModelAttribute("vehicle") @Valid Vehicle vehicle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "vehicles/addVehicle";
        }
        vehicleRepository.save(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/details/{id}")
    public String vehicleDetails(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        model.addAttribute("vehicle", vehicle);
        return "/vehicles/vehicleDetails";
    }

    @GetMapping("/update/{id}")
    public String updateVehicle(Model model, @PathVariable Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        model.addAttribute("vehicle", vehicle);
        return "vehicles/editVehicle";
    }

    @PostMapping("/update-action")
    public String updateVehicle(@ModelAttribute("vehicle") @Valid Vehicle vehicle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "vehicles/editVehicle";
        }
        vehicleRepository.save(vehicle);
        return "redirect:/vehicles/details/" + vehicle.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "vehicles/deleteVehicle";
    }

    @GetMapping("/delete-action/{id}")
    public String deleteEmployeeAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            Vehicle vehicle = vehicleRepository.findById(id).get();
            vehicleRepository.delete(vehicle);
            return "redirect:/vehicles";
        } else {
            return "redirect:/vehicles/details/" + id;
        }
    }

    @ModelAttribute("vehicles")
    public List<Vehicle> getEmployees() {
        return vehicleRepository.findAll();
    }

    @ModelAttribute("fuelTypes")
    public Set<FuelType> getFuelTypes() {
        return EnumSet.allOf(FuelType.class);
    }

    @ModelAttribute("customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
