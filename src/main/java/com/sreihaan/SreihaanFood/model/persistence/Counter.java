package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "counter")
public class Counter {

    @Id
    private String  id;

    private long seq;

}
