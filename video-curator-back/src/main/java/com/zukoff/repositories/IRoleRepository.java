package com.zukoff.repositories;

import com.zukoff.entities.Comment;
import com.zukoff.entities.Role;
import com.zukoff.enums.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IRoleRepository extends CrudRepository<Role,Integer> {
    public Role findByRole(RoleEnum role);
}