package com.zukoff.services;

import com.zukoff.entities.Role;
import com.zukoff.enums.RoleEnum;
import com.zukoff.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private IRoleRepository roleRepository;

    @Autowired
    public void setRepository(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByRole(RoleEnum role){
        return this.roleRepository.findByRole(role);
    }
}
