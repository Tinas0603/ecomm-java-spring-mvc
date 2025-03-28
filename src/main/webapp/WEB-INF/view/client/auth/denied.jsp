<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>404 - Không Tìm Thấy</title>
                <link rel="stylesheet" href="/client/css/denied.css">
            </head>

            <body>
                <div class="container">
                    <div class="error-code">404</div>
                    <div class="message">Oops! Trang bạn tìm không tồn tại.</div>
                    <a href="/" class="back-btn">Quay lại trang chủ</a>
                </div>

                <!-- Tạo hiệu ứng hạt nổi -->
                <script>
                    function createParticle() {
                        const particle = document.createElement('div');
                        particle.classList.add('particle');
                        particle.style.left = Math.random() * 100 + 'vw';
                        particle.style.animationDuration = Math.random() * 3 + 2 + 's';
                        document.body.appendChild(particle);
                        setTimeout(() => particle.remove(), 5000);
                    }
                    setInterval(createParticle, 200);
                </script>
            </body>

            </html>