package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.servicestation.EntityNotFoundException;
import pl.coderslab.servicestation.models.Invoice;
import pl.coderslab.servicestation.models.Order;
import pl.coderslab.servicestation.models.Part;
import pl.coderslab.servicestation.repositories.InvoiceRepository;
import pl.coderslab.servicestation.repositories.OrderRepository;
import pl.coderslab.servicestation.repositories.PartRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StatusService statusService;
    private final PartRepository partRepository;
    private final InvoiceRepository invoiceRepository;

    public void saveOrder(Order order) {
        if (order.getPlannedRepairStart().equals(LocalDate.now())) {
            order.setActualRepairStart(LocalDate.now());
            order.setStatus(statusService.findById(2L));
        } else {
            order.setStatus(statusService.findById(1L));
        }
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        order.setUpdated(LocalDate.now());

        if (order.getStatus().getId() == 1) {
            order.setActualRepairStart(null);
        }

        if (order.getStatus().getId() == 2) {
            if (order.getActualRepairStart() == null) {
                order.setActualRepairStart(LocalDate.now());
            }
        }
        orderRepository.save(order);
    }

    public void attachPartToOrder(Order order, Part part) {

        order.getParts().add(part);

        order.getParts().stream()
                .forEach(p -> {
                    p.setOrder(order);
                    partRepository.save(p);
                });
    }

    public void deletePart(Long partId) {
        Part part = partRepository.findById(partId).orElseThrow(() -> new EntityNotFoundException(partId, Part.class.getSimpleName()));
        partRepository.delete(part);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Order.class.getSimpleName()));
    }

    public void deleteOrder(Long id) {
        Order order = findById(id);
        orderRepository.delete(order);
    }

    public void startRepair(Long id) {
        Order order = findById(id);
        order.setStatus(statusService.findById(2L));
        order.setActualRepairStart(LocalDate.now());
        order.setUpdated(LocalDate.now());
        updateOrder(order);
    }

    public void finishRepair(Order order) {
        order.setStatus(statusService.findById(3L));
        order.setUpdated(LocalDate.now());
        updateOrder(order);
    }

    public void cancelRepair(Order order) {
        order.setStatus(statusService.findById(4L));
        order.setUpdated(LocalDate.now());
        updateOrder(order);
    }

    public List<Order> getFinishedAndCancelledOrders() {
        return orderRepository.findFinishedAndCancelledOrders();
    }

    public List<Order> getCurrentOrdersByVehicleId(Long id) {
        return orderRepository.findCurrentOrdersByVehicleId(id);
    }

    public List<Order> getHistoricalOrdersByVehicleId(Long id) {
        return orderRepository.findHistoryOrdersByVehicleId(id);
    }

    public void addInvoiceToOrder(Long orderId, MultipartFile file) {
        if (!file.isEmpty()) {
            Order order = findById(orderId);
            StringUtils.cleanPath(file.getOriginalFilename());
            Invoice invoiceToAdd = new Invoice();
            try {
                String code = Base64.getEncoder().encodeToString(file.getBytes());
                invoiceToAdd.setCode(code);

                order.getInvoices().add(invoiceToAdd);

                order.getInvoices().stream()
                        .forEach(i -> {
                            i.setOrder(order);
                            invoiceRepository.save(i);
                        });

                order.setUpdated(LocalDate.now());
                updateOrder(order);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
