package com.sreihaan.SreihaanFood.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateCategoryRequest {

    private long id;

    private String name;

    private String description;

}
