package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.servicestation.models.Customer;
import pl.coderslab.servicestation.models.FuelType;
import pl.coderslab.servicestation.models.Vehicle;
import pl.coderslab.servicestation.services.CustomerService;
import pl.coderslab.servicestation.services.VehicleService;

import javax.validation.Valid;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final CustomerService customerService;

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
        vehicleService.saveVehicle(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/details/{id}")
    public String vehicleDetails(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleService.findById(id);
        model.addAttribute("vehicle", vehicle);
        return "/vehicles/vehicleDetails";
    }

    @GetMapping("/update/{id}")
    public String updateVehicle(Model model, @PathVariable Long id) {
        Vehicle vehicle = vehicleService.findById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles/editVehicle";
    }

    @PostMapping("/update-action")
    public String updateVehicle(@ModelAttribute("vehicle") @Valid Vehicle vehicle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "vehicles/editVehicle";
        }
        vehicleService.saveVehicle(vehicle);
        return "redirect:/vehicles/details/" + vehicle.getId();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete-action/{id}")
    public String deleteVehicleAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            vehicleService.deleteVehicle(id);
            return "redirect:/vehicles";
        } else {
            return "redirect:/vehicles/update/" + id;
        }
    }

    @ModelAttribute("vehicles")
    public List<Vehicle> getVehicles() {
        return vehicleService.findAll();
    }

    @ModelAttribute("fuelTypes")
    public Set<FuelType> getFuelTypes() {
        return EnumSet.allOf(FuelType.class);
    }

    @ModelAttribute("customers")
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }
}
