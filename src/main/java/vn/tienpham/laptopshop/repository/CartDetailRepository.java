package vn.tienpham.laptopshop.repository;

import org.springframework.stereotype.Repository;

import vn.tienpham.laptopshop.domain.CartDetail;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
}
