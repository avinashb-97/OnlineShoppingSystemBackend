package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.exception.DataNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.Address;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.repository.AddressRepository;
import com.sreihaan.SreihaanFood.service.AddressService;
import com.sreihaan.SreihaanFood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    @Override
    public Address addAddress(Address address) {
        User user = userService.getCurrentUser();
        return addAddress(address, user);
    }

    @Override
    public Address addAddress(Address address, User user) {
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(long id, Address address)
    {
        Address savedAddress = getAddressById(id);
        savedAddress.setCustomerName(address.getCustomerName());
        savedAddress.setAddressLine1(address.getAddressLine1());
        savedAddress.setAddressLine2(address.getAddressLine2());
        savedAddress.setCity(address.getCity());
        savedAddress.setState(address.getState());
        savedAddress.setCountry(address.getCountry());
        savedAddress.setPostalCode(address.getPostalCode());
        savedAddress.setPhone(address.getPhone());
        return savedAddress;
    }

    @Override
    public List<Address> getAddresses() {
        User user = userService.getCurrentUser();
        return user.getAddresses();
    }

    public Address getAddressById(long id)
    {
        return addressRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Address not found, id -> "+id));
    }

    @Override
    public void deleteAddress(long id) {
        Address address = getAddressById(id);
        addressRepository.delete(address);
    }
}
