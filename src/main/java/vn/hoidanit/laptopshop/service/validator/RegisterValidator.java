package vn.hoidanit.laptopshop.service.validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.UserService;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {
    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

        // kiểm tra xem hai trường password và confirmPassword có khớp nhau hay không.
        if (user.getPassword() != null && !user.getPassword().isEmpty() &&
                user.getConfirmPassword() != null && !user.getConfirmPassword().isEmpty() &&
                !user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Passwords và Confirm Password không khớp")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // kiểm tra độ mạnh của mật khẩu
        if (!user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$")) {
            context.buildConstraintViolationWithTemplate(
                    "Password phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt")
                    .addPropertyNode("password")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }
        // check email
        // kiểm tra email không được để trống
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Email không được để trống")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        } else {
            // check email đã tồn tại
            if (this.userService.checkEmailExist(user.getEmail())) {
                context.buildConstraintViolationWithTemplate("Email đã tồn tại")
                        .addPropertyNode("email")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
                valid = false;
            }
        }
        // kiểm tra firstName và lastName
        if (user.getFirstName() == null || user.getFirstName().length() < 1) {
            context.buildConstraintViolationWithTemplate("Tên phải có tối thiểu 1 ký tự")
                    .addPropertyNode("firstName")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        if (user.getLastName() == null || user.getLastName().length() < 1) {
            context.buildConstraintViolationWithTemplate("Họ phải có tối thiểu 1 ký tự")
                    .addPropertyNode("lastName")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }
        // kiểm tra fullName
        // String fullName = user.getFirstName() + " " + user.getLastName();
        // if (fullName.length() < 3) {
        // context.buildConstraintViolationWithTemplate("Tên đầy đủ phải có tối thiểu 3
        // ký tự")
        // .addPropertyNode("firstName")
        // .addConstraintViolation()
        // .disableDefaultConstraintViolation();
        // valid = false;
        // }
        return valid;
    }
}
