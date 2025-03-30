package vn.tienpham.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.tienpham.laptopshop.service.OrderService;
import vn.tienpham.laptopshop.service.ProductService;
import vn.tienpham.laptopshop.service.UserService;

@Controller

public class DashBoardController {

    private final OrderService orderService;

    private final ProductService productService;
    private final UserService userService;

    public DashBoardController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        model.addAttribute("countUsers", userService.countUsers());
        model.addAttribute("countProducts", productService.countProducts());
        model.addAttribute("countOrders", orderService.countOrders());
        return "admin/dashboard/show";
    }
}
