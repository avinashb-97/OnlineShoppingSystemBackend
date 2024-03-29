package com.sreihaan.SreihaanFood.model.persistence.repository;

import com.sreihaan.SreihaanFood.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public Optional<User> findUserByEmailIgnoreCase(String email);

    public boolean existsUserByEmailIgnoreCase(String email);

}