package com.sreihaan.SreihaanFood.model.persistence.repository;

import com.sreihaan.SreihaanFood.model.persistence.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    public Optional<User> findUserByEmailIgnoreCase(String email);

    public boolean existsUserByEmailIgnoreCase(String email);

    public Optional<User> findUserByConfirmationToken(String confiramtionToken);

}