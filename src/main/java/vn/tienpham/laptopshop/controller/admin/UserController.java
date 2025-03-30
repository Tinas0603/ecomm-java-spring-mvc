package vn.tienpham.laptopshop.controller.admin;

import vn.tienpham.laptopshop.service.RoleService;
import vn.tienpham.laptopshop.service.UploadService;
import vn.tienpham.laptopshop.service.UserService;
import vn.tienpham.laptopshop.domain.User;

import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model, @RequestParam(defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 5); // 5 người dùng mỗi trang
        Page<User> userPage = this.userService.getAllUsers(pageable);
        List<User> users = userPage.getContent();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        return "admin/user/show";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        return "admin/user/create";
    }

    // @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    @PostMapping("/admin/user/create")
    public String createNewUser(
            @ModelAttribute("newUser") @Valid User user,
            BindingResult newUserBindingResult,
            @RequestParam("newFile") MultipartFile file) {
        // validate
        // List<FieldError> errors = newUserBindingResult.getFieldErrors();
        // for (FieldError error : errors) {
        // System.out.println(error.getField() + " - " + error.getDefaultMessage());
        // }
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String avatar = this.uploadService.handleSaveUploadFile(file, "avatars");
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setAvatar(avatar);
        user.setPassword(hashPassword);
        user.setRole(this.roleService.getRoleByName(user.getRole().getName()));
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/update/{id}") // GET
    public String getUpdateUserPage(@PathVariable long id, Model model) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("updateUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(
            @ModelAttribute("updateUser") User user,
            @RequestParam(value = "updateFile") MultipartFile file,
            HttpServletRequest request) { // Thêm HttpServletRequest để truy cập session
        User currentUser = this.userService.getUserById(user.getId());
        if (currentUser != null) {
            if (file != null && !file.isEmpty()) {
                String avatar = this.uploadService.handleSaveUploadFile(file, "avatars");
                currentUser.setAvatar(avatar);
            }
            currentUser.setAddress(user.getAddress());
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());
            currentUser.setRole(this.roleService.getRoleByName(user.getRole().getName()));
            this.userService.handleSaveUser(currentUser);

            // Cập nhật session nếu người dùng được cập nhật là tài khoản đang đăng nhập
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("id") != null) {
                long loggedInUserId = (long) session.getAttribute("id");
                if (loggedInUserId == currentUser.getId()) { // Kiểm tra xem có phải tài khoản đang đăng nhập không
                    session.setAttribute("fullName", currentUser.getFullName());
                    session.setAttribute("avatar", currentUser.getAvatar());
                }
            }
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        User currentUser = new User();
        currentUser.setId(id);
        model.addAttribute("deleteUser", currentUser);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(@ModelAttribute("deleteUser") User user) {
        this.userService.deleteAUser(user.getId());
        return "redirect:/admin/user";
    }
}