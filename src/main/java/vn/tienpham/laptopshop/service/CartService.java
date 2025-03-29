package vn.tienpham.laptopshop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.tienpham.laptopshop.domain.Cart;
import vn.tienpham.laptopshop.domain.CartDetail;
import vn.tienpham.laptopshop.domain.Product;
import vn.tienpham.laptopshop.domain.User;
import vn.tienpham.laptopshop.repository.CartRepository;
import vn.tienpham.laptopshop.repository.ProductRepository;
import vn.tienpham.laptopshop.repository.CartDetailRepository;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public CartService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public void addProductToCart(String Email, long productId) {
        User user = this.userService.getUserByEmail(Email);
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            // check xem User đã có Cart chưa ? Nếu chưa thì tạo mới
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(1);
                cart = this.cartRepository.save(newCart);
            }
            // check xem Product đã có trong Cart chưa ? Nếu chưa thì tạo mới
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                CartDetail cd = new CartDetail();
                cd.setCart(cart);
                cd.setProduct(realProduct);
                cd.setPrice(realProduct.getPrice());
                cd.setQuantity(1);
                this.cartDetailRepository.save(cd);
            }
        }
    }

}
