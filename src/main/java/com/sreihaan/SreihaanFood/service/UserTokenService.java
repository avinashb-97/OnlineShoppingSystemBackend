package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.UserToken;

public interface UserTokenService {

    public UserToken GenerateUserConfirmationToken(User user);

    public User getUserAndDeleteConfirmationToken(String confirmationToken);

    UserToken generateForgotPasswordToken(User user);

    User getUserAndDeleteResetToken(String resetToken);
}
