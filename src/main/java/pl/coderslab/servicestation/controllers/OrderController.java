package pl.coderslab.servicestation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.servicestation.models.*;
import pl.coderslab.servicestation.services.*;
import pl.coderslab.servicestation.validationGroups.CancelledOrderGroup;
import pl.coderslab.servicestation.validationGroups.CreatedOrderGroup;
import pl.coderslab.servicestation.validationGroups.FinishedOrderGroup;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final EmployeeService employeeService;
    private final VehicleService vehicleService;
    private final StatusService statusService;
    private final InvoiceService invoiceService;

    @GetMapping("/add")
    public String addOrder(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);

        return "orders/addOrder";
    }

    @PostMapping("/add-action")
    public String addOrderExecute(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/addOrder";
        }

        orderService.saveOrder(order);

        return "redirect:/home";
    }

    @GetMapping("/manage-parts/{orderId}")
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

        return "redirect:/orders/manage-parts/" + orderId;
    }


    @GetMapping("/delete-part/{partId}/{orderId}")
    public String deletePart2(@PathVariable Long partId, @PathVariable Integer orderId) {
        orderService.deletePart(partId);

        return "redirect:/orders/manage-parts/" + orderId;
    }

    @PostMapping("/upload-invoice-action/{orderId}")
    public String uploadInvoiceAction(@PathVariable("orderId") Long orderId, @RequestParam("file") MultipartFile file) {

        orderService.addInvoiceToOrder(orderId, file);

        return "redirect:/orders/details/" + orderId;
    }

    @GetMapping("/delete-invoice/{invoiceId}/{orderId}")
    public String deleteInvoice(@PathVariable("invoiceId") Long invoiceId, @PathVariable("orderId") Long orderId) {
        invoiceService.deleteInvoice(invoiceId);

        orderService.updateOrder(orderService.findById(orderId));

        return "redirect:/orders/details/" + orderId;
    }

    @GetMapping("/details/{id}")
    public String orderDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        Part part = new Part();
        model.addAttribute("part", part);
        return "orders/orderCourse";
    }

    @PostMapping("/repair-progress-report-update-action/{orderId}")
    public String getRepairProgressReportAction(@PathVariable("orderId") Long orderId,
                                                @RequestParam("report") String report) {
        Order order = orderService.findById(orderId);
        order.setRepairProgressReport(report);
        orderService.updateOrder(order);

        return "redirect:/orders/details/" + orderId;
    }


    @GetMapping("/start-repair-action/{orderId}")
    public String changeStatusAction(@PathVariable("orderId") Long orderId, @RequestParam("action") Boolean action) {
        if (action) {
            orderService.startRepair(orderId);
        } else {
            return "redirect:/orders/details/" + orderId;
        }
        return "redirect:/orders/details/" + orderId;
    }

    @GetMapping("/finish-repair/{orderId}")
    public String finishRepair(Model model, @PathVariable("orderId") Long orderId) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "orders/finishRepair";
    }

    @PostMapping("/finish-repair-action")
    public String finishRepairAction(@ModelAttribute("order") @Validated({Default.class, FinishedOrderGroup.class}) Order order,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/finishRepair";
        }

        orderService.finishRepair(order);

        return "redirect:/orders/details/" + order.getId();
    }

    @GetMapping("/cancel-repair/{orderId}")
    public String cancelRepair(Model model, @PathVariable("orderId") Long orderId) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "orders/cancelRepair";
    }

    @PostMapping("/cancel-repair-action")
    public String cancelRepairAction(@ModelAttribute("order") @Validated({Default.class, CancelledOrderGroup.class}) Order order,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/cancelRepair";
        }

        orderService.cancelRepair(order);

        return "redirect:/orders/details/" + order.getId();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/update/{id}")
    public String updateCustomer(Model model, @PathVariable Long id) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "orders/editOrder";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/update-action")
    public String updateCustomer(Model model, @ModelAttribute("order") @Validated({Default.class, CreatedOrderGroup.class}) Order order,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/editOrder";
        }
        if(order.getStatus().getId()==3 && order.getActualRepairStart()==null){
            model.addAttribute("finishedWithoutStartDate", "true");
            return "orders/editOrder";
        }

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

    @RequestMapping("/vehicleCurrentOrders/{vehicleId}")
    public String vehicleCurrentOrdersView(Model model, @PathVariable("vehicleId") Long vehicleId) {
        List<Order> vehicleCurrentOrders = orderService.getCurrentOrdersByVehicleId(vehicleId);
        Vehicle vehicle = vehicleService.findById(vehicleId);
        model.addAttribute("vehicleCurrentOrders", vehicleCurrentOrders);
        model.addAttribute("vehicle", vehicle);
        return "orders/currentOrdersForVehicle";
    }

    @RequestMapping("/vehicleHistoryOrders/{vehicleId}")
    public String vehicleHistoryOrdersView(Model model, @PathVariable("vehicleId") Long vehicleId) {
        List<Order> vehicleHistoryOrders = orderService.getHistoricalOrdersByVehicleId(vehicleId);
        Vehicle vehicle = vehicleService.findById(vehicleId);
        model.addAttribute("vehicleHistoryOrders", vehicleHistoryOrders);
        model.addAttribute("vehicle", vehicle);
        return "orders/historyOrdersForVehicle";
    }

    @RequestMapping("/historyOrders")
    public String historyOrdersView() {
        return "orders/historyOrders";
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
