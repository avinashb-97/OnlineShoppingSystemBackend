package com.sreihaan.SreihaanFood.model.page;

import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class OrderPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String sortBy = "createdTime";
    private Status status = null;
}
