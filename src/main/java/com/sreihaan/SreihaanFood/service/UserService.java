package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.User;
import org.springframework.stereotype.Service;

public interface UserService {

    public User createUser(User user, String password);

    public User getUserByEmail(String email);

}
