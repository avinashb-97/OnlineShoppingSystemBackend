package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.exception.InvalidConfirmationTokenException;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.repository.UserRepository;
import com.sreihaan.SreihaanFood.service.CounterService;
import com.sreihaan.SreihaanFood.service.EmailSenderService;
import com.sreihaan.SreihaanFood.service.UserService;
import com.sreihaan.SreihaanFood.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    @Override
    public User createUser(User user, String password) {
        if(userRepository.existsUserByEmailIgnoreCase(user.getEmail()))
        {
            return user;
        }
        User savedUser = addUser(user, password);
        emailSenderService.sendEmail(savedUser.getEmail(), "Complete Registration !", "To confirm your account, please click here : "
                + MailUtil.getConfirmationLink(savedUser.getConfirmationToken()));
        return savedUser;
    }

    private User addUser(User user, String password)
    {

        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setId(counterService.getNextSequence("user").toString());
        user.setPersisted(true);
        user.setConfirmationToken(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .orElse(new User());
    }

    @Override
    public User confirmUser(String confirmationToken) {
        User user = userRepository.findUserByConfirmationToken(confirmationToken)
                .orElseThrow(()->new InvalidConfirmationTokenException("The link is invalid or Broken"));
        user.setEnabled(true);
        return userRepository.save(user);
    }

}
