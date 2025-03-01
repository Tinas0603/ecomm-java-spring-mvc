package vn.tienpham.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.tienpham.laptopshop.domain.Role;
import vn.tienpham.laptopshop.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
