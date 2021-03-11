package com.sreihaan.SreihaanFood.controller;


import com.sreihaan.SreihaanFood.dto.AddressDTO;
import com.sreihaan.SreihaanFood.model.persistence.Address;
import com.sreihaan.SreihaanFood.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/user/address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public AddressDTO addAddress(@RequestBody AddressDTO addressDTO)
    {
        Address address = AddressDTO.convertAddressDTOToEntity(addressDTO);
        address = addressService.addAddress(address);
        return AddressDTO.convertEntityToAddressDTO(address);
    }

    @PutMapping("/{id}")
    public AddressDTO updateAddress(@PathVariable long id, @RequestBody AddressDTO addressDTO)
    {
        Address address = AddressDTO.convertAddressDTOToEntity(addressDTO);
        address = addressService.updateAddress(id, address);
        return AddressDTO.convertEntityToAddressDTO(address);
    }

    @GetMapping
    public List<AddressDTO> getAddresses()
    {
        List<Address> addresses = addressService.getAddresses();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for(Address address : addresses)
        {
            AddressDTO addressDTO = AddressDTO.convertEntityToAddressDTO(address);
            addressDTOList.add(addressDTO);
        }
        return addressDTOList;
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable long id)
    {
        addressService.deleteAddress(id);
    }

}
