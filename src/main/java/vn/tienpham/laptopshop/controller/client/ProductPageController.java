package vn.tienpham.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.tienpham.laptopshop.service.ProductService;

@Controller
public class ProductPageController {
    final ProductService productService;

    public ProductPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        model.addAttribute("product", this.productService.getProductById(id));
        return "client/product/detail";
    }
}
