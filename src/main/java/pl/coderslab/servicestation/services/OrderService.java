package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.servicestation.models.Order;
import pl.coderslab.servicestation.models.Part;
import pl.coderslab.servicestation.repositories.*;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final VehicleRepository vehicleRepository;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final StatusRepository statusRepository;
    private final PartRepository partRepository;
    private final InvoiceRepository invoiceRepository;

    public void saveOrder(Order order) {
        if (order.getPlannedRepairStart().equals(LocalDate.now())) {
            order.setActualRepairStart(LocalDate.now());
            order.setStatus(statusRepository.findById(2L).get());
        } else {
            order.setStatus(statusRepository.findById(1L).get());
        }
        orderRepository.save(order);
    }

    public void attachPartsToOrder(Order order, Part part) {
        partRepository.save(part);

        order.getParts().add(part);

        order.getParts().stream()
                .forEach(v -> {
                    v.setOrder(order);
                    partRepository.save(v);
                });

        orderRepository.save(order);
    }

    public Order findById(Long id) {
        Order order = orderRepository.findById(id).get();
        return order;
    }


}
