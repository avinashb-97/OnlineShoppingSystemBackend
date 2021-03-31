package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {

    public User createUser(User user, String password);

    public User getUserByEmail(String email);

    public User findUserById(Long id);

    public User confirmUser(String confirmationToken);

    void resetPassword(String email, String password);

    void changePasswordForCurrentUser(String oldPassword, String password);

    Integer generateOTP(String email);

    boolean verifyOTP(String email, int otp);

    void deleteUser(String email);

    public User getCurrentUser();

    User createDummyUser(String email);
}
