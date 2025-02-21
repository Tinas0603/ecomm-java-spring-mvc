package vn.hoidanit.laptopshop.controller.client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class ProductPageController {
    final ProductService productService;

    public ProductPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        List<Product> products = productService.getAllProducts();
        // Lấy danh sách category duy nhất từ products
        Set<String> categories = products.stream()
                .map(Product::getTarget)
                .collect(Collectors.toSet());

        model.addAttribute("categories", categories);
        model.addAttribute("product", this.productService.getProductById(id));
        return "client/product/detail";
    }
}
