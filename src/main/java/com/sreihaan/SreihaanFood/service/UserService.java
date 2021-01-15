package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {

    public User createUser(User user, String password);

    public User getUserByEmail(String email);

    public User confirmUser(String confirmationToken);
}
