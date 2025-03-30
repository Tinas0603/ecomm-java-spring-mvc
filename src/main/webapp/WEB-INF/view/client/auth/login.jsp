<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Login - Laptopshop</title>
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
                                        style="font-family: 'Raleway', sans-serif;">Đăng Nhập</h3>
                                </div>
                                <div class="card-body p-4">
                                    <form method="post" action="/login">
                                        <c:if test="${param.error != null}">
                                            <div class="alert alert-danger">Email hoặc mật khẩu không đúng.</div>
                                        </c:if>
                                        <c:if test="${param.logout != null}">
                                            <div class="alert alert-success">Đăng xuất thành công.</div>
                                        </c:if>
                                        <div class="form-group mb-3">
                                            <label style="font-family: 'Open Sans', sans-serif;">Email</label>
                                            <div class="input-group">
                                                <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                                <input class="form-control rounded-pill" type="email"
                                                    placeholder="name@example.com" name="username" required />
                                            </div>
                                        </div>
                                        <div class="form-group mb-3">
                                            <label style="font-family: 'Open Sans', sans-serif;">Mật khẩu</label>
                                            <div class="input-group">
                                                <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                                <input class="form-control rounded-pill" type="password"
                                                    placeholder="Password" name="password" id="password" required />
                                                <span class="input-group-text toggle-password" style="cursor: pointer;">
                                                    <i class="fas fa-eye" id="togglePasswordIcon"></i>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="form-check mb-3">
                                            <input class="form-check-input" type="checkbox" name="remember-me"
                                                id="rememberMe" />
                                            <label class="form-check-label"
                                                style="font-family: 'Open Sans', sans-serif;" for="rememberMe">Ghi nhớ
                                                tôi</label>
                                        </div>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        <button class="btn border border-secondary rounded-pill px-4 text-primary w-100"
                                            type="submit">
                                            <i class="fas fa-sign-in-alt me-2"></i> Đăng Nhập
                                        </button>
                                    </form>
                                </div>
                                <div class="card-footer text-center py-3">
                                    <small style="font-family: 'Open Sans', sans-serif;">
                                        Chưa có tài khoản? <a href="/register" class="text-primary">Đăng ký ngay</a>
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/js/main.js"></script>
                <script>
                    const togglePassword = document.querySelector('.toggle-password');
                    const password = document.querySelector('#password');
                    const togglePasswordIcon = document.querySelector('#togglePasswordIcon');

                    togglePassword.addEventListener('click', function () {
                        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
                        password.setAttribute('type', type);
                        togglePasswordIcon.classList.toggle('fa-eye');
                        togglePasswordIcon.classList.toggle('fa-eye-slash');
                    });
                </script>
                <jsp:include page="../layout/footer.jsp" />
            </body>

            </html>