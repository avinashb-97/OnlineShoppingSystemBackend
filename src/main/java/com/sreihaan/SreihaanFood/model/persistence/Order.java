package com.sreihaan.SreihaanFood.model.persistence;


import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import io.github.kaiso.relmongo.annotation.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Setter
@Getter
@Document(collection = "order")
public class Order implements Persistable<Long> {

    @Id
    private long Long;

    @ManyToOne(mappedBy = "orders")
    private User user;

    private Map<Long, Long> contents;

    private BigDecimal total;

    private Status status;

    @CreatedDate
    private Date createdTime = new Date();

    @LastModifiedDate
    private Date lastModifiedTime;

    private boolean persisted;

    @Override
    public java.lang.Long getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }
}
