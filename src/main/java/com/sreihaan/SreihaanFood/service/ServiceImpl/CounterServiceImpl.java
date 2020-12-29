package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.model.persistence.Counter;
import com.sreihaan.SreihaanFood.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private MongoOperations mongo;

    public Long getNextSequence(String collectionName) {

        Counter counter = mongo.findAndModify(
                Query.query(Criteria.where("_id").is(collectionName)),
                new Update().inc("seq",1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                Counter.class);
        return counter.getSeq();
    }

}
