package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.constants.MailConstants;
import com.sreihaan.SreihaanFood.exception.UserNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.UserToken;
import com.sreihaan.SreihaanFood.model.persistence.repository.UserRepository;
import com.sreihaan.SreihaanFood.service.CounterService;
import com.sreihaan.SreihaanFood.service.EmailSenderService;
import com.sreihaan.SreihaanFood.service.UserService;
import com.sreihaan.SreihaanFood.service.UserTokenService;
import com.sreihaan.SreihaanFood.utils.AuthUtil;
import com.sreihaan.SreihaanFood.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@EnableMongoAuditing
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CounterService counterService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserTokenService userTokenService;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createUser(User user, String password) {
        if(userRepository.existsUserByEmailIgnoreCase(user.getEmail()))
        {
            return user;
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setId(counterService.getNextSequence("user").toString());
        user.setPersisted(true);
        user = userRepository.save(user);
        UserToken userToken = userTokenService.GenerateUserConfirmationToken(user);
        String confirmationToken = userToken.getToken();
        emailSenderService.sendEmail(user.getEmail(), MailConstants.USER_CONFIRMATION_SUBJECT, MailConstants.USER_CONFIRMATION_BODY + MailUtil.getUserConfirmationLink(confirmationToken));
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found, email -> "+email));
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(user != null && user.isEnabled())
        {
            UserToken forgotPasswordToken = userTokenService.generateForgotPasswordToken(user);
            String resetToken = forgotPasswordToken.getToken();
            logger.info("[Forgot Password] Reset token generated, user: "+user.getEmail()+" token: "+resetToken);
            emailSenderService.sendEmail(user.getEmail(), MailConstants.PASSWORD_RESET_SUBJECT, MailConstants.PASSWORD_RESET_BODY+ MailUtil.getUserConfirmationLink(resetToken));
        }
        else
        {
            logger.info("[Forgot Password] User not found or not enabled, email: "+email+" isEnabled: "+ (user !=null ? user.isEnabled() : "Not exists"));
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

}
