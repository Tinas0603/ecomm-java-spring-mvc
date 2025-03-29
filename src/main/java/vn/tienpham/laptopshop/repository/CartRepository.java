package vn.tienpham.laptopshop.repository;

import org.springframework.stereotype.Repository;

import vn.tienpham.laptopshop.domain.Cart;
import vn.tienpham.laptopshop.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
