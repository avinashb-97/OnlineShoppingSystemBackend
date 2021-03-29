package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Address;
import com.sreihaan.SreihaanFood.model.persistence.User;

import java.util.List;

public interface AddressService {

    public Address addAddress(Address address);

    public List<Address> getAddresses();

    public void deleteAddress(long id);

    public Address updateAddress(long id, Address address);

    public Address getAddressById(long id);

    public Address addAddress(Address address, User user);
}
