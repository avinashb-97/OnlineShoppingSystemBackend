package com.sreihaan.SreihaanFood.model.persistence.repository;


import com.sreihaan.SreihaanFood.model.persistence.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends MongoRepository<Counter, String > {
}
