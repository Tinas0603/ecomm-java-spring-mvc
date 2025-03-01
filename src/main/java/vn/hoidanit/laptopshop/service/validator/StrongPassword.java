package vn.hoidanit.laptopshop.service.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD }) // chỉ định rằng annotation này sẽ được áp dụng ở cấp độ field.
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrongPassword {
    String message() default "Mật khẩu phải chứa ít nhất một chữ số, một chữ cái thường, một chữ cái hoa, một ký tự đặc biệt và có độ dài ít nhất 8 ký tự.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
