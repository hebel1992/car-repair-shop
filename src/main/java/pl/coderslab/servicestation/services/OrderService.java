package pl.coderslab.servicestation.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.servicestation.models.Invoice;
import pl.coderslab.servicestation.models.Order;
import pl.coderslab.servicestation.models.Part;
import pl.coderslab.servicestation.repositories.InvoiceRepository;
import pl.coderslab.servicestation.repositories.OrderRepository;
import pl.coderslab.servicestation.repositories.PartRepository;
import pl.coderslab.servicestation.repositories.StatusRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
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

    public void updateOrder(Order order){
        orderRepository.save(order);
    }

    public void attachPartToOrder(Order order, Part part) {

        order.getParts().add(part);

        order.getParts().stream()
                .forEach(v -> {
                    v.setOrder(order);
                    partRepository.save(v);
                });
    }

    public void deletePart(Long partId) {
        Part part = partRepository.findById(partId).get();
        partRepository.delete(part);
    }

    public Order findById(Long id) {
        Order order = orderRepository.findById(id).get();
        return order;
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        orderRepository.delete(order);
    }

    public void changeStatus(Long orderId, Long statusId) {
        Order order = findById(orderId);
        order.setStatus(statusRepository.findById(statusId).get());
        orderRepository.save(order);
    }

    public List<Order> getFinishedAndCancelledOrders() {
        return orderRepository.findFinishedAndCancelledOrders();
    }

    public List<Order> getHistoricalOrdersByVehicleId(Long id) {
        return orderRepository.findHistoryOrdersByVehicleId(id);
    }

    public void addInvoiceToOrder(Long orderId, MultipartFile file) {
        if (!file.isEmpty()) {
            Order order = findById(orderId);
            StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Invoice invoiceToAdd = new Invoice();
                invoiceToAdd.setFile(file.getBytes());

                order.getInvoices().add(invoiceToAdd);

                order.getInvoices().stream()
                        .forEach(i -> {
                            i.setOrder(order);
                            invoiceRepository.save(i);
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
