package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.servicestation.models.*;
import pl.coderslab.servicestation.repositories.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final VehicleRepository vehicleRepository;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final StatusRepository statusRepository;
    private final PartRepository partRepository;
    private final InvoiceRepository invoiceRepository;

    @GetMapping("/add/{orderId}")
    public String addOrder(Model model, @PathVariable String orderId) {
        if ("empty".equals(orderId)) {
            Order order = new Order();
            model.addAttribute("order", order);
            return "orders/addOrder";
        } else {
            Order order = orderRepository.findById(Long.parseLong(orderId)).get();
            model.addAttribute("order", order);
        }
        return "orders/addOrder";
    }

    @PostMapping("/add-action")
    public String addOrderExecute(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/addOrder";
        }
        if (order.getPlannedRepairStart().equals(LocalDate.now())) {
            order.setActualRepairStart(LocalDate.now());
            order.setStatus(statusRepository.findById(2L).get());
        } else {
            order.setStatus(statusRepository.findById(1L).get());
        }
        orderRepository.save(order);
        return "redirect:/orders/add-part-to-order/" + order.getId();
    }

    @GetMapping("/add-part-to-order/{orderId}")
    public String addPartsToOrder(Model model, @PathVariable Long orderId) {
        Part part = new Part();
        Order order = orderRepository.findById(orderId).get();
        model.addAttribute("part", part);
        model.addAttribute("addedParts", order.getParts());
        return "orders/addPartsToOrder";
    }

    @PostMapping("/add-part-to-order-action/{orderId}")
    public String addPartExecute(Model model, @PathVariable Long orderId, @ModelAttribute("part") @Valid Part part, BindingResult bindingResult) {
        Order order = orderRepository.findById(orderId).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("addedParts", order.getParts());
            return "orders/addPartsToOrder";
        }
        partRepository.save(part);

        order.getParts().add(part);

        order.getParts().stream()
                .forEach(v -> {
                    v.setOrder(order);
                    partRepository.save(v);
                });

        orderRepository.save(order);

        model.addAttribute("addedParts", order.getParts());

        return "redirect:/orders/add-part-to-order/" + orderId;
    }

    @GetMapping("/add-last-page/{orderId}")
    public String addOrderLastPage(Model model, @PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        model.addAttribute("order", order);
        return "orders/addOrderFinalPage";
    }

    @PostMapping("/add-last-page-action")
    public String addOrderLastPageAction(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/addOrderFinalPage";
        }
        orderRepository.save(order);
        return "redirect:/";
    }


    @GetMapping("/delete-part/{partId}/{orderId}")
    public String deletePart(@PathVariable Long partId, @PathVariable Integer orderId) {
        Part part = partRepository.findById(partId).get();
        partRepository.delete(part);
        return "redirect:/orders/add-part-to-order/" + orderId;
    }


    @GetMapping("/details/{id}")
    public String customerDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderRepository.findById(id).get();
        Set<Employee> employeesAssignedToOrder = employeeRepository.findEmployeesByOrderId(id);
        model.addAttribute("order", order);
        model.addAttribute("employeesAssignedToOrder", employeesAssignedToOrder);

        return "/orders/orderDetails";
    }

    @GetMapping("/change-status/{statusId}/{orderId}")
    public String changeStatus(Model model, @PathVariable("statusId") Integer statusId, @PathVariable("orderId") Long orderId) {
        model.addAttribute("statusId", statusId);
        model.addAttribute("orderId", orderId);
        return "orders/startRepair";
    }

    @GetMapping("/change-status-action/{statusId}/{orderId}")
    public String changeStatusAction(@PathVariable("statusId") Long statusId, @PathVariable("orderId") Long orderId, @RequestParam("action") Boolean action) {
        if (action) {
            Order order = orderRepository.findById(orderId).get();
            order.setStatus(statusRepository.findById(statusId).get());
            orderRepository.save(order);
        } else {
            return "redirect:/orders/details/" + orderId;
        }
        return "redirect:/orders/details/" + orderId;
    }

    @GetMapping("/update/{id}")
    public String updateCustomer(Model model, @PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Set<Employee> employeesNotInOrder = employeeRepository.findAllEmployeesNotInOrder(id);
        model.addAttribute("order", order);
        model.addAttribute("employeesNotInOrder", employeesNotInOrder);
        return "orders/editOrder";
    }

    @PostMapping("/update-action")
    public String updateCustomer(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/editOrder";
        }
        Order orderBeforeUpdate = orderRepository.findById(order.getId()).get();

        order.getEmployees().addAll(orderBeforeUpdate.getEmployees());
        order.setUpdated(LocalDate.now());
        order.setActualRepairStart(LocalDate.now());
        orderRepository.save(order);
        return "redirect:/orders/details/" + order.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "orders/deleteOrder";
    }

    @GetMapping("/delete-action/{id}")
    public String deleteCustomerAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            Order order = orderRepository.findById(id).get();
            orderRepository.delete(order);
            return "redirect:/";
        } else {
            return "redirect:/orders/details/" + id;
        }
    }

    @GetMapping("/detachEmployee/{employeeId}/{orderId}")
    public String detachVehicle(@PathVariable Long employeeId, @PathVariable Long orderId, Model model) {
        Order order = orderRepository.findById(orderId).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        order.getEmployees().remove(employee);
        orderRepository.save(order);

        return "redirect:/orders/update/" + orderId;
    }

    @RequestMapping("/historyOrders")
    public String historyOrdersView() {
        return "orders/historyOrders";
    }

    @RequestMapping("/vehicleHistoryOrders/{vehicleId}")
    public String vehicleHistoryOrdersView(Model model, @PathVariable("vehicleId") Long vehicleId) {
        List<Order> vehicleHistoryOrders = orderRepository.findHistoryOrdersByVehicleId(vehicleId);
        Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
        model.addAttribute("vehicleHistoryOrders", vehicleHistoryOrders);
        model.addAttribute("vehicleInHistory", vehicle);
        return "/orders/historyOrdersForVehicle";
    }

    @GetMapping("/upload-invoice/{orderId}")
    public String uploadInvoice(@PathVariable("orderId") Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "/orders/uploadInvoiceForm";
    }

    @PostMapping("/upload-invoice-execute/{orderId}")
    public String uploadInvoiceExecute(@PathVariable("orderId") Long orderId, @RequestParam("file") MultipartFile file) {

        Order order = orderRepository.findById(orderId).get();

        if (!file.isEmpty()) {
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

        orderRepository.save(order);

        return "redirect:/orders/details/" + orderId;
    }

    @GetMapping("/full-screen-invoice/{imageId}")
    public String getFullScreenInvoice(@PathVariable("imageId") Long imageId, Model model) {
        Invoice invoice = invoiceRepository.findById(imageId).get();

        byte[] file = invoice.getFile();
        String image = "";
        if (file != null && file.length > 0) {
            image = Base64.getEncoder().encodeToString(file);
        }
        model.addAttribute("image", image);
        return "/orders/fullScreenInvoice";
    }

    @GetMapping("/delete-invoice/{invoiceId}/{orderId}")
    public String deleteInvoice(@PathVariable("invoiceId") Long invoiceId, @PathVariable("orderId") Long orderId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        invoiceRepository.delete(invoice);

        return "redirect:/orders/details/" + orderId;
    }

    @ModelAttribute("historyOrders")
    public List<Order> historyOrders() {
        return orderRepository.findFinishedAndCancelledOrders();
    }

    @ModelAttribute("vehicles")
    public List<Vehicle> getCustomers() {
        return vehicleRepository.findAll();
    }

    @ModelAttribute("employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @ModelAttribute("statusOptions")
    public List<Status> getStatusList() {
        return statusRepository.findAll();
    }
}
