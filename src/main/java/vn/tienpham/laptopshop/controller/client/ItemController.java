package vn.tienpham.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.tienpham.laptopshop.domain.Cart;
import vn.tienpham.laptopshop.domain.CartDetail;
import vn.tienpham.laptopshop.domain.Product;
import vn.tienpham.laptopshop.domain.User;
import vn.tienpham.laptopshop.service.CartService;
import vn.tienpham.laptopshop.service.OrderService;
import vn.tienpham.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {

    final OrderService orderService;
    final ProductService productService;
    final CartService cartService;

    public ItemController(ProductService productService, CartService cartService, OrderService orderService) {
        this.productService = productService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product pr = this.productService.getProductById(id).get();
        model.addAttribute("product", pr);
        return "client/product/detail";
    }

    @GetMapping("/products")
    public String getProductPage(Model model, @RequestParam(defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Product> prs = this.productService.getAllProducts(pageable);
        List<Product> products = prs.getContent();

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "client/product/show";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");
        this.cartService.addProductToCart(email, productId, session);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.cartService.fetchByUser(currentUser);

        // Kiểm tra nếu cart là null
        List<CartDetail> cartDetails = new ArrayList<>();
        double totalPrice = 0;

        if (cart != null) {
            cartDetails = cart.getCartDetails();
            for (CartDetail cd : cartDetails) {
                totalPrice += cd.getPrice() * cd.getQuantity();
            }
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        this.cartService.handleRemoveCartDetail(id, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.cartService.fetchByUser(currentUser);

        // Kiểm tra nếu cart là null
        List<CartDetail> cartDetails = new ArrayList<>();
        double totalPrice = 0;

        if (cart != null) {
            cartDetails = cart.getCartDetails();
            for (CartDetail cd : cartDetails) {
                totalPrice += cd.getPrice() * cd.getQuantity();
            }
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = new ArrayList<>();
        if (cart != null)
            cartDetails = cart.getCartDetails();
        this.cartService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone,
            @RequestParam("paymentMethod") String paymentMethod) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        this.orderService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone,
                paymentMethod);
        if (!paymentMethod.equals("COD")) {

        }
        return "redirect:/thank-you";
    }

    @GetMapping("/thank-you")
    public String getThankYouPage(Model model) {

        return "client/cart/thank-you";
    }

    @PostMapping("/add-product-to-cart-in-product-detail-page/{id}")
    public String addProductToCartInProductDetailPage(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");
        this.cartService.addProductToCart(email, productId, session);
        return "redirect:/product/" + productId;
    }
}
