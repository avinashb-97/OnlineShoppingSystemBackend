package com.sreihaan.SreihaanFood.model.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Hashtable;

@Getter
@Setter
public class ModifyCartRequest {

    Hashtable<Long, Long> products;

}
