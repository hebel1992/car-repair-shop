package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.servicestation.models.*;
import pl.coderslab.servicestation.services.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final EmployeeService employeeService;
    private final VehicleService vehicleService;
    private final StatusService statusService;
    private final InvoiceService invoiceService;

    @GetMapping("/add/{orderId}")
    public String addOrder(Model model, @PathVariable String orderId) {
        if ("empty".equals(orderId)) {
            Order order = new Order();
            model.addAttribute("order", order);
            return "orders/addOrder";
        } else {
            Order order = orderService.findById(Long.parseLong(orderId));
            model.addAttribute("order", order);
        }
        return "orders/addOrder";
    }

    @PostMapping("/add-action")
    public String addOrderExecute(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/addOrder";
        }

        orderService.saveOrder(order);

        return "redirect:/orders/add-part-to-order/" + order.getId();
    }

    @GetMapping("/add-part-to-order/{orderId}")
    public String addPartsToOrder(Model model, @PathVariable Long orderId) {
        Part part = new Part();
        Order order = orderService.findById(orderId);
        model.addAttribute("part", part);
        model.addAttribute("addedParts", order.getParts());
        return "orders/addPartsToOrder";
    }

    @PostMapping("/add-part-to-order-action/{orderId}")
    public String addPartExecute(Model model, @PathVariable Long orderId, @ModelAttribute("part") @Valid Part part, BindingResult bindingResult) {
        Order order = orderService.findById(orderId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("addedParts", order.getParts());
            return "orders/addPartsToOrder";
        }

        orderService.attachPartToOrder(order, part);

        model.addAttribute("addedParts", order.getParts());

        return "redirect:/orders/add-part-to-order/" + orderId;
    }

    @GetMapping("/add-last-page/{orderId}")
    public String addOrderLastPage(Model model, @PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "orders/addOrderFinalPage";
    }

    @PostMapping("/add-last-page-action")
    public String addOrderLastPageAction(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/addOrderFinalPage";
        }
        orderService.saveOrder(order);
        return "redirect:/home";
    }


    @GetMapping("/delete-part/{partId}/{orderId}")
    public String deletePart(@PathVariable Long partId, @PathVariable Integer orderId) {
        orderService.deletePart(partId);

        return "redirect:/orders/add-part-to-order/" + orderId;
    }


    @GetMapping("/details/{id}")
    public String orderDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id);
        Set<Employee> employeesAssignedToOrder = employeeService.findEmployeesByOrderId(id);
        model.addAttribute("order", order);
        model.addAttribute("employeesAssignedToOrder", employeesAssignedToOrder);

        return "/orders/orderDetails";
    }

    @GetMapping("/start-repair/{statusId}/{orderId}")
    public String changeStatus(Model model, @PathVariable("statusId") Long statusId, @PathVariable("orderId") Long orderId) {
        model.addAttribute("statusId", statusId);
        model.addAttribute("orderId", orderId);
        return "orders/startRepair";
    }

    @GetMapping("/start-repair-action/{statusId}/{orderId}")
    public String changeStatusAction(@PathVariable("statusId") Long statusId, @PathVariable("orderId") Long orderId, @RequestParam("action") Boolean action) {
        if (action) {
            orderService.startRepair(orderId, statusId);
        } else {
            return "redirect:/orders/details/" + orderId;
        }
        return "redirect:/orders/details/" + orderId;
    }

    @GetMapping("/update/{id}")
    public String updateCustomer(Model model, @PathVariable Long id) {
        Order order = orderService.findById(id);
        Set<Employee> employeesNotInOrder = employeeService.findAllEmployeesNotInThisOrder(id);
        model.addAttribute("order", order);
        model.addAttribute("employeesNotInOrder", employeesNotInOrder);
        return "orders/editOrder";
    }

    @PostMapping("/update-action")
    public String updateCustomer(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/editOrder";
        }

        Order orderBeforeUpdate = orderService.findById(order.getId());

        order.getEmployees().addAll(orderBeforeUpdate.getEmployees());
        order.setUpdated(LocalDate.now());
        order.setActualRepairStart(LocalDate.now());

        orderService.updateOrder(order);

        return "redirect:/orders/details/" + order.getId();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteCustomer(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "orders/deleteOrder";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete-action/{id}")
    public String deleteCustomerAction(@PathVariable Long id, @RequestParam("action") boolean action) {
        if (action) {
            orderService.deleteOrder(id);
            return "redirect:/home";
        } else {
            return "redirect:/orders/details/" + id;
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/detachEmployee/{employeeId}/{orderId}")
    public String detachVehicle(@PathVariable Long employeeId, @PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        Employee employee = employeeService.findById(employeeId);

        order.getEmployees().remove(employee);
        orderService.updateOrder(order);

        return "redirect:/orders/update/" + orderId;
    }

    @RequestMapping("/historyOrders")
    public String historyOrdersView() {
        return "orders/historyOrders";
    }

    @RequestMapping("/vehicleHistoryOrders/{vehicleId}")
    public String vehicleHistoryOrdersView(Model model, @PathVariable("vehicleId") Long vehicleId) {
        List<Order> vehicleHistoryOrders = orderService.getHistoricalOrdersByVehicleId(vehicleId);
        Vehicle vehicle = vehicleService.findById(vehicleId);
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

        orderService.addInvoiceToOrder(orderId, file);

        return "redirect:/orders/details/" + orderId;
    }

    @GetMapping("/full-screen-invoice/{imageId}")
    public String getFullScreenInvoice(@PathVariable("imageId") Long imageId, Model model) {
        Invoice invoice = invoiceService.findById(imageId);

        model.addAttribute("image", invoice.getCode());
        return "/orders/fullScreenInvoice";
    }

    @GetMapping("/delete-invoice/{invoiceId}/{orderId}")
    public String deleteInvoice(@PathVariable("invoiceId") Long invoiceId, @PathVariable("orderId") Long orderId) {
        invoiceService.deleteInvoice(invoiceId);

        return "redirect:/orders/details/" + orderId;
    }

    @ModelAttribute("historyOrders")
    public List<Order> historyOrders() {
        return orderService.getFinishedAndCancelledOrders();
    }

    @ModelAttribute("vehicles")
    public List<Vehicle> getCustomers() {
        return vehicleService.findAll();
    }

    @ModelAttribute("employees")
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }

    @ModelAttribute("statusOptions")
    public List<Status> getStatusList() {
        return statusService.statusList();
    }
}
