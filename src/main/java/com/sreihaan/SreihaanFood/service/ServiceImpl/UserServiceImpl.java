package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.constants.MailConstants;
import com.sreihaan.SreihaanFood.constants.SecurityConstants;
import com.sreihaan.SreihaanFood.exception.UserNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.UserToken;
import com.sreihaan.SreihaanFood.model.persistence.enums.Role;
import com.sreihaan.SreihaanFood.model.persistence.repository.UserRepository;
import com.sreihaan.SreihaanFood.service.*;
import com.sreihaan.SreihaanFood.utils.AuthUtil;
import com.sreihaan.SreihaanFood.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private CartService cartService;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getCurrentUser()
    {
        return getUserByEmail(AuthUtil.getLoggedInUserName());
    }

    @Override
    public User createUser(User user, String password) {
        if(userRepository.existsUserByEmailIgnoreCase(user.getEmail()))
        {
            return user;
        }
        if(user.getEmail().equals(SecurityConstants.MAIN_ADMIN_EMAIL))
        {
            user = addAdminRoleToUser(user);
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEnabled(true);
        user = userRepository.save(user);
        cartService.createCart(user);
//        UserToken userToken = userTokenService.GenerateUserConfirmationToken(user);
//        String confirmationToken = userToken.getToken();
//        emailSenderService.sendEmail(user.getEmail(), MailConstants.USER_CONFIRMATION_SUBJECT, MailConstants.USER_CONFIRMATION_BODY + MailUtil.getUserConfirmationLink(confirmationToken));
        return user;
    }

    private User addAdminRoleToUser(User user)
    {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);
        roleSet.add(Role.MODERATOR);
        roleSet.add(Role.ADMIN);
        user.setRoles(roleSet);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found, email -> "+email));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id.toString())
                .orElseThrow(() -> new UserNotFoundException("User not found, userid -> "+id));
    }

    @Override
    public User confirmUser(String confirmationToken) {
        User user = userTokenService.getUserAndDeleteConfirmationToken(confirmationToken);
        user.setEnabled(true);
        user.setUserToken(null);
        return userRepository.save(user);
    }

    @Override
    public void forgotPassword(String email) {
        User user = null;
        try {
            user = getUserByEmail(email);
            logger.info("[Forgot Password] User Found, Email: "+user.getEmail()+" isEnabled: "+user.isEnabled());
            if(user.isEnabled())
            {
                UserToken forgotPasswordToken = userTokenService.generateForgotPasswordToken(user);
                String resetToken = forgotPasswordToken.getToken();
                logger.info("[Forgot Password] Reset token generated, user: "+user.getEmail()+" token: "+resetToken);
                emailSenderService.sendEmail(user.getEmail(), MailConstants.PASSWORD_RESET_SUBJECT, MailUtil.getUserPasswordResetMessage(resetToken));
            }
        }
        catch (Exception e)
        {
            String exception = e.getMessage();
            logger.info("[Forgot Password] User not found, email: "+email+" Exception: "+exception);
        }

    }

    @Override
    public void resetPassword(String resetToken, String password) {
        User user = userTokenService.getUserAndDeleteResetToken(resetToken);
        logger.info("[Reset Password] User found successfully, user: "+user.getEmail()+" token: "+resetToken);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        logger.info("[Reset Password] Password Reset Success, user: "+user.getEmail());
    }

    @Override
    public void changePasswordForCurrentUser(String oldPassword, String password) {
        User user = userRepository.findUserByEmailIgnoreCase(AuthUtil.getLoggedInUserName())
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        if(!BCrypt.checkpw(oldPassword, user.getPassword()))
        {
            throw new SecurityException("Invalid Password given");
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);

    }

    @Override
    public Integer generateOTP(String email) {
        Integer otp = otpService.generateOTP(email);
        emailSenderService.sendEmail(email, "OTP", "Your OTP is "+otp);
        return otp;
    }

    @Override
    public boolean verifyOTP(String email, int userOTP) {
        Integer otp = otpService.getOtp(email);
        return userOTP == otp && otp != null && otp != 0;
    }

    @Override
    public void deleteUser(String email) {
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }

}
