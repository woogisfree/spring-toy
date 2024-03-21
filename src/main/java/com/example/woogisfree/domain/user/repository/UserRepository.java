package com.example.woogisfree.domain.user.repository;

import com.example.woogisfree.domain.user.entity.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String username);

    Optional<ApplicationUser> findByEmail(String email);
}
