package vn.hoidanit.laptopshop.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // kiểm tra xem chuỗi mật khẩu có chứa ít nhất một chữ số, một chữ cái thường,
        // một chữ cái hoa, một ký tự đặc biệt và có độ dài ít nhất 8 ký tự hay không.
        return value.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$");
    }
}