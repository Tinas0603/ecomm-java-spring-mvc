<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8">
                <title>Quản lý tài khoản - Laptopshop</title>
                <meta content="width=device-width, initial-scale=1.0" name="viewport">
                <meta content="" name="keywords">
                <meta content="" name="description">

                <!-- Google Web Fonts -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                    rel="stylesheet">

                <!-- Icon Font Stylesheet -->
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                    rel="stylesheet">

                <!-- Libraries Stylesheet -->
                <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

                <!-- Customized Bootstrap Stylesheet -->
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                <!-- Template Stylesheet -->
                <link href="/client/css/style.css" rel="stylesheet">

                <!-- Preview Image Script -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        const orgImage = "${user.avatar}";
                        if (orgImage) {
                            const urlImage = "/images/avatars/" + orgImage;
                            $("#avatarPreview").attr("src", urlImage);
                            $("#avatarPreview").css({ "display": "block" });
                        }

                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>
            </head>

            <body>
                <!-- Spinner Start -->
                <div id="spinner"
                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
                    <div class="spinner-grow text-primary" role="status"></div>
                </div>
                <!-- Spinner End -->

                <jsp:include page="../layout/header.jsp" />

                <!-- Account Page Start -->
                <div class="container-fluid py-5">
                    <div class="container py-5">
                        <h1 class="mb-4">Quản lý tài khoản</h1>
                        <div class="row">
                            <div class="col-md-6">
                                <h3>Thông tin tài khoản</h3>
                                <p><strong>Email:</strong> ${user.email}</p>
                                <p><strong>Họ và tên:</strong> ${user.fullName}</p>
                                <p><strong>Số điện thoại:</strong> ${user.phone}</p>
                                <p><strong>Địa chỉ:</strong> ${user.address}</p>
                                <!-- <img src="/images/avatars/${user.avatar}" alt="Avatar" class="img-fluid rounded"
                                    style="max-width: 150px;" /> -->
                            </div>
                            <div class="col-md-6">
                                <h3>Cập nhật thông tin</h3>
                                <form:form method="post" action="/account/update" modelAttribute="user"
                                    enctype="multipart/form-data">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <div class="mb-3" style="display: none;">
                                        <form:input type="hidden" path="id" />
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Họ và tên: <span class="text-danger">*</span></label>
                                        <form:input type="text" class="form-control" path="fullName" required="true" />
                                        <form:errors path="fullName" cssClass="text-danger" />
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Địa chỉ:</label>
                                        <form:input type="text" class="form-control" path="address" />
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Số điện thoại: <span
                                                class="text-danger">*</span></label>
                                        <form:input type="text" class="form-control" path="phone" required="true" />
                                        <form:errors path="phone" cssClass="text-danger" />
                                    </div>
                                    <div class="mb-3">
                                        <label for="avatarFile" class="form-label">Ảnh đại diện:</label>
                                        <input type="file" class="form-control" id="avatarFile" name="avatarFile"
                                            accept=".png, .jpg, .jpeg" />
                                    </div>
                                    <div class="mb-3">
                                        <img style="max-height: 250px; display: none;" alt="avatar preview"
                                            id="avatarPreview" class="img-fluid" />
                                    </div>
                                    <c:if test="${not empty error}">
                                        <div class="alert alert-danger">${error}</div>
                                    </c:if>
                                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Account Page End -->

                <jsp:include page="../layout/footer.jsp" />

                <!-- Back to Top -->
                <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                        class="fa fa-arrow-up"></i></a>

                <!-- JavaScript Libraries -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/lib/easing/easing.min.js"></script>
                <script src="/client/lib/waypoints/waypoints.min.js"></script>
                <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                <!-- Template Javascript -->
                <script src="/client/js/main.js"></script>
            </body>

            </html>