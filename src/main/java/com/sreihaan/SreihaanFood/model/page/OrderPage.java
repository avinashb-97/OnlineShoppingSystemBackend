package com.sreihaan.SreihaanFood.model.page;

import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Date;

@Getter
@Setter
public class OrderPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "createdTime";
    private Status status = null;
    private Date startDate = null;
    private Date endDate = null;
}
