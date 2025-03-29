package vn.tienpham.laptopshop.repository;

import org.springframework.stereotype.Repository;

import vn.tienpham.laptopshop.domain.Cart;
import vn.tienpham.laptopshop.domain.CartDetail;
import vn.tienpham.laptopshop.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    boolean existsByCartAndProduct(Cart cart, Product product);

    CartDetail findByCartAndProduct(Cart cart, Product product);
}
