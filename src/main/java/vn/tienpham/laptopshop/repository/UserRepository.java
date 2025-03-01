package vn.tienpham.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.tienpham.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User findOneByEmail(String email);
}
