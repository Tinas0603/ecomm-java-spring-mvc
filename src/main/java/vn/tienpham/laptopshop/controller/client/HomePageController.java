package vn.tienpham.laptopshop.controller.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.tienpham.laptopshop.domain.Order;
import vn.tienpham.laptopshop.domain.Product;
import vn.tienpham.laptopshop.domain.User;
import vn.tienpham.laptopshop.domain.dto.RegisterDTO;
import vn.tienpham.laptopshop.service.OrderService;
import vn.tienpham.laptopshop.service.ProductService;
import vn.tienpham.laptopshop.service.RoleService;
import vn.tienpham.laptopshop.service.UploadService;
import vn.tienpham.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;
    private final UploadService uploadService;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder,
            RoleService roleService, OrderService orderService, UploadService uploadService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.orderService = orderService;
        this.uploadService = uploadService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, @RequestParam(defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 10); // 10 sản phẩm mỗi trang
        Page<Product> prs = this.productService.getAllProducts(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {
        // validate
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = this.userService.registerDTOtoUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.roleService.getRoleByName("USER"));
        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }

    @GetMapping("/access-denied")
    public String getDeniedPage(Model model) {
        return "client/auth/denied";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);
        return "client/cart/order-history";
    }

    @PostMapping("/order-history/cancel/{id}")
    public String handleCancelOrder(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login"; // Chuyển hướng về login nếu chưa đăng nhập
        }

        long userId = (long) session.getAttribute("id");
        Optional<Order> orderOptional = this.orderService.fetchOrderById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            // Kiểm tra xem đơn hàng có thuộc về user hiện tại và ở trạng thái PENDING không
            if (order.getUser().getId() == userId && "PENDING".equals(order.getStatus())) {
                order.setStatus("CANCEL");
                this.orderService.updateOrder(order);
            }
        }

        return "redirect:/order-history";
    }

    @GetMapping("/account")
    public String getAccountPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login"; // Chuyển hướng về login nếu chưa đăng nhập
        }

        long userId = (long) session.getAttribute("id");
        User currentUser = this.userService.getUserById(userId);
        model.addAttribute("user", currentUser);
        return "client/account/manage"; // Trả về trang quản lý tài khoản
    }

    @PostMapping("/account/update")
    public String handleUpdateAccount(
            @ModelAttribute("user") User user,
            @RequestParam(value = "avatarFile", required = false) MultipartFile file,
            HttpServletRequest request,
            Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        long userId = (long) session.getAttribute("id");
        User currentUser = this.userService.getUserById(userId);

        if (currentUser != null) {
            // Kiểm tra fullName không được để trống
            if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
                model.addAttribute("error", "Tên không được để trống!");
                model.addAttribute("user", currentUser);
                return "client/account/manage";
            }
            if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
                model.addAttribute("error", "Số điện thoại không được để trống!");
                model.addAttribute("phone", currentUser);
                return "client/account/manage";
            }

            // Ghi đè fullName (đã kiểm tra không null/rỗng)
            currentUser.setFullName(user.getFullName().trim());

            // Ghi đè address cho phép null
            currentUser.setAddress(user.getAddress() != null ? user.getAddress().trim() : null);

            // Xử lý file ảnh nếu có
            if (file != null && !file.isEmpty()) {
                String avatar = this.uploadService.handleSaveUploadFile(file, "avatars");
                currentUser.setAvatar(avatar);
            }

            // Lưu thông tin người dùng đã cập nhật
            this.userService.handleSaveUser(currentUser);

            // Cập nhật lại session với thông tin mới
            session.setAttribute("fullName", currentUser.getFullName());
            session.setAttribute("avatar", currentUser.getAvatar());
        }

        return "redirect:/account";
    }

}
