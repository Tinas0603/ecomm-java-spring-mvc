<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Register - Laptopshop</title>
                <!-- Google Fonts -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                    rel="stylesheet">
                <!-- Font Awesome -->
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <!-- Bootstrap -->
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">
                <!-- Custom CSS -->
                <link href="/client/css/style.css" rel="stylesheet">
            </head>

            <body>
                <jsp:include page="../layout/header.jsp" />
                <div class="container py-5 mt-5">
                    <div class="row justify-content-center">
                        <div class="col-lg-6">
                            <div class="card shadow border-0 rounded-3 mt-5">
                                <div class="card-header bg-white text-center py-4">
                                    <h3 class="font-weight-bold text-primary"
                                        style="font-family: 'Raleway', sans-serif;">Tạo Tài Khoản</h3>
                                </div>
                                <div class="card-body p-4">
                                    <form:form method="post" action="/register" modelAttribute="registerUser">
                                        <c:set var="errorFirstName">
                                            <form:errors path="firstName" cssClass="text-danger small" />
                                        </c:set>
                                        <c:set var="errorLastName">
                                            <form:errors path="lastName" cssClass="text-danger small" />
                                        </c:set>
                                        <c:set var="errorEmail">
                                            <form:errors path="email" cssClass="text-danger small" />
                                        </c:set>
                                        <c:set var="errorPassword">
                                            <form:errors path="password" cssClass="text-danger small" />
                                        </c:set>
                                        <c:set var="errorConfirmPassword">
                                            <form:errors path="confirmPassword" cssClass="text-danger small" />
                                        </c:set>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label style="font-family: 'Open Sans', sans-serif;">Tên</label>
                                                <form:input
                                                    class="form-control rounded-pill ${not empty errorFirstName ? 'is-invalid' : ''}"
                                                    type="text" placeholder="Nhập tên" path="firstName" />
                                                ${errorFirstName}
                                            </div>
                                            <div class="col-md-6">
                                                <label style="font-family: 'Open Sans', sans-serif;">Họ</label>
                                                <form:input
                                                    class="form-control rounded-pill ${not empty errorLastName ? 'is-invalid' : ''}"
                                                    type="text" placeholder="Nhập họ" path="lastName" />
                                                ${errorLastName}
                                            </div>
                                        </div>
                                        <div class="form-group mb-3">
                                            <label style="font-family: 'Open Sans', sans-serif;">Email</label>
                                            <div class="input-group">
                                                <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                                <form:input
                                                    class="form-control rounded-pill ${not empty errorEmail ? 'is-invalid' : ''}"
                                                    type="email" placeholder="name@example.com" path="email" />
                                            </div>
                                            ${errorEmail}
                                        </div>
                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label style="font-family: 'Open Sans', sans-serif;">Mật khẩu</label>
                                                <div class="input-group">
                                                    <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                                    <form:input
                                                        class="form-control rounded-pill ${not empty errorPassword ? 'is-invalid' : ''}"
                                                        type="password" placeholder="Tạo mật khẩu" path="password"
                                                        id="password" />
                                                    <span class="input-group-text toggle-password"
                                                        style="cursor: pointer;">
                                                        <i class="fas fa-eye" id="togglePasswordIcon"></i>
                                                    </span>
                                                </div>
                                                ${errorPassword}
                                            </div>
                                            <div class="col-md-6">
                                                <label style="font-family: 'Open Sans', sans-serif;">Xác nhận mật
                                                    khẩu</label>
                                                <div class="input-group">
                                                    <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                                    <form:input
                                                        class="form-control rounded-pill ${not empty errorConfirmPassword ? 'is-invalid' : ''}"
                                                        type="password" placeholder="Xác nhận mật khẩu"
                                                        path="confirmPassword" id="confirmPassword" />
                                                    <span class="input-group-text toggle-confirm-password"
                                                        style="cursor: pointer;">
                                                        <i class="fas fa-eye" id="toggleConfirmPasswordIcon"></i>
                                                    </span>
                                                </div>
                                                ${errorConfirmPassword}
                                            </div>
                                        </div>
                                        <button class="btn border border-secondary rounded-pill px-4 text-primary w-100"
                                            type="submit">
                                            <i class="fas fa-user-plus me-2"></i> Tạo Tài Khoản
                                        </button>
                                    </form:form>
                                </div>
                                <div class="card-footer text-center py-3">
                                    <small style="font-family: 'Open Sans', sans-serif;">
                                        Đã có tài khoản? <a href="/login" class="text-primary">Đăng nhập</a>
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/js/main.js"></script>
                <script>
                    // Toggle Password
                    const togglePassword = document.querySelector('.toggle-password');
                    const password = document.querySelector('#password');
                    const togglePasswordIcon = document.querySelector('#togglePasswordIcon');

                    togglePassword.addEventListener('click', function () {
                        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
                        password.setAttribute('type', type);
                        togglePasswordIcon.classList.toggle('fa-eye');
                        togglePasswordIcon.classList.toggle('fa-eye-slash');
                    });

                    // Toggle Confirm Password
                    const toggleConfirmPassword = document.querySelector('.toggle-confirm-password');
                    const confirmPassword = document.querySelector('#confirmPassword');
                    const toggleConfirmPasswordIcon = document.querySelector('#toggleConfirmPasswordIcon');

                    toggleConfirmPassword.addEventListener('click', function () {
                        const type = confirmPassword.getAttribute('type') === 'password' ? 'text' : 'password';
                        confirmPassword.setAttribute('type', type);
                        toggleConfirmPasswordIcon.classList.toggle('fa-eye');
                        toggleConfirmPasswordIcon.classList.toggle('fa-eye-slash');
                    });
                </script>
                <jsp:include page="../layout/footer.jsp" />
            </body>

            </html>