package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.repository.UserRepository;
import com.sreihaan.SreihaanFood.service.CounterService;
import com.sreihaan.SreihaanFood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
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

    @Override
    public User createUser(User user, String password) {
        if(userRepository.existsUserByEmailIgnoreCase(user.getEmail()))
        {
            return user;
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setId(counterService.getNextSequence("user").toString());
        user.setPersisted(true);
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmailIgnoreCase(email)
                .orElse(new User());
    }

}
