package vn.tienpham.laptopshop.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.tienpham.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
