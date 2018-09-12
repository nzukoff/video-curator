package com.zukoff.repositories;

import com.zukoff.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User,Integer> {
    User findByUsername(String username);
}
