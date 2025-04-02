# Java Spring MVC Ecommerce Website

## 📖 Mô tả dự án

Đây là một dự án **Ecommerce Website** được xây dựng bằng **Java Spring MVC**, mô phỏng một cửa hàng bán laptop trực tuyến (Laptopshop). Dự án cung cấp các tính năng cơ bản của một website thương mại điện tử, bao gồm quản lý sản phẩm, giỏ hàng, đăng nhập/đăng ký người dùng, và đặt hàng. Mục tiêu của dự án là học hỏi và áp dụng các công nghệ Java hiện đại trong phát triển web.

Dự án được thiết kế với kiến trúc **MVC (Model-View-Controller)**, sử dụng **Spring Framework** để quản lý các thành phần và tích hợp **Spring Security** để bảo mật. Giao diện người dùng được xây dựng với **Bootstrap** và **JSP**, đảm bảo tính responsive và thân thiện với người dùng.

---

## 🚀 Tính năng chính

- **Sản phẩm**:
  - Xem danh sách sản phẩm.
  - Xem chi tiết sản phẩm.
- **Giỏ hàng**:
  - Thêm sản phẩm vào giỏ hàng.
  - Xem và chỉnh sửa giỏ hàng (tăng/giảm số lượng, xóa sản phẩm).
  - Đặt hàng và nhận thông báo cảm ơn.
  - Tích hợp ví điện tử VNPay
  - Xem lịch sử đặt hàng, huỷ đơn hàng nếu vẫn còn trong trạng thái "PENDING"
- **Người dùng**:
  - Đăng ký tài khoản, Đăng nhập, đăng xuất (tích hợp Spring Security).
  - Hỗ trợ tính năng "Remember Me" để tự động đăng nhập.
  - Trang hồ sơ cá nhân để xem và sửa lại thông tin theo ý muốn.
- **Bảo mật**:
  - Xác thực và phân quyền với Spring Security.
  - Bảo vệ form đăng nhập với CSRF token.
- **Giao diện**:
  - Responsive design với Bootstrap 5.
  - Hỗ trợ các hiệu ứng như spinner, carousel, và lightbox.
- **ADMIN quản lý**:
  - Quản lý người dùng, đơn hàng, sản phẩm, phân quyền theo Role.
---

## 🛠 Công nghệ sử dụng

Dự án sử dụng các công nghệ và công cụ sau:

- **Backend**:
  - **Java**
  - **Spring Framework**: (Spring MVC, Spring Security, Spring Data JPA)
- **Spring Session** : duy trì phiên đăng nhập của người dùng
  - **Maven**: Quản lý dependency
- **Database**:
  - **MySQL**: (lưu trữ dữ liệu người dùng, sản phẩm, giỏ hàng)
- **Frontend**:
  - **JSP**: View engine cho giao diện,sử dụng JSTL (Jakarta Standard Tag Library)
  - **Bootstrap**:(CSS framework)
  - **FontAwesome**:(icon)
  - **jQuery**: (hỗ trợ JavaScript)
  - **Owl Carousel**: Hiệu ứng carousel cho sản phẩm
  - **Lightbox**: Hiển thị hình ảnh sản phẩm
- **Công cụ phát triển**:
  - **IDE**: VSCode
  - **Tomcat**: (web server)
  - **Git**: Quản lý mã nguồn

---
