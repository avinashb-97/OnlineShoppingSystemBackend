package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.User;

public interface UserService {

    public User createUser(User user, String password);

    public User getUserByEmail(String email);

}
