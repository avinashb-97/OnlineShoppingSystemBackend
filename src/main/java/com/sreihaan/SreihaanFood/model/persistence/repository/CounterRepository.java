package com.sreihaan.SreihaanFood.model.persistence.repository;


import com.sreihaan.SreihaanFood.model.persistence.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter, String > {
}
