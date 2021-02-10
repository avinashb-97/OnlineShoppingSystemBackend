package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.constants.SecurityConstants;
import com.sreihaan.SreihaanFood.exception.InvalidConfirmationTokenException;
import com.sreihaan.SreihaanFood.exception.InvalidPasswordResetToken;
import com.sreihaan.SreihaanFood.exception.PasswordTokenExpiredException;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.UserToken;
import com.sreihaan.SreihaanFood.model.persistence.repository.UserTokenRepository;
import com.sreihaan.SreihaanFood.service.CounterService;
import com.sreihaan.SreihaanFood.service.UserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    private static Logger logger = LoggerFactory.getLogger(UserTokenServiceImpl.class);

    @Override
    public UserToken GenerateUserConfirmationToken(User user) {
        UserToken userToken = generateTokenForUser(user);
        UserToken savedToken = userTokenRepository.save(userToken);
        return savedToken;
    }

    @Override
    public User getUserAndDeleteConfirmationToken(String confirmationToken) {
        UserToken userToken = userTokenRepository.findUserTokenByToken(confirmationToken)
                .orElseThrow(()->new InvalidConfirmationTokenException("The link is invalid or Broken"));
        User user = userToken.getUser();
        userTokenRepository.delete(userToken);
        return user;
    }

    private UserToken generateTokenForUser(User user)
    {
        UserToken userToken = new UserToken();
        String token = UUID.randomUUID().toString();
        userToken.setToken(token);
        userToken.setUser(user);
        return userToken;
    }

    private Date getExpiryTimeForPasswordToken()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, SecurityConstants.PASSWORD_TOKEN_EXPIRATION);
        return calendar.getTime();
    }

    @Override
    public UserToken generateForgotPasswordToken(User user)
    {
        UserToken userToken = null;
        try {
            userToken = new UserToken(user.getUserToken());
            logger.info("[Forgot Password] Updating password token for user -> "+user.getEmail());
            userToken.setToken(UUID.randomUUID().toString());
        }
        catch (Exception e)
        {
            userToken = generateTokenForUser(user);
            logger.info("[Forgot Password] Generating new password token for user -> "+user.getEmail());
        }
        userToken.setExpiryDate(getExpiryTimeForPasswordToken());
        return userTokenRepository.save(userToken);
    }

    @Override
    public User getUserAndDeleteResetToken(String token)
    {
        UserToken passwordToken = userTokenRepository.findUserTokenByToken(token)
                .orElseThrow(()->new InvalidPasswordResetToken("The link is invalid or Broken"));
        if(isPasswordTokenExpired(passwordToken))
        {
            logger.info("Password token expired, user -> "+ passwordToken.getUser().getEmail()+" Expired time -> "+ passwordToken.getExpiryDate());
            throw new PasswordTokenExpiredException("Password token expired");
        }
        User user = passwordToken.getUser();
        userTokenRepository.delete(passwordToken);
        return user;
    }

    private boolean isPasswordTokenExpired(UserToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
