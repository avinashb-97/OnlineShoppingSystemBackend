package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.UserDTO;
import com.sreihaan.SreihaanFood.model.persistence.Role;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.requests.CreateUserRequest;
import com.sreihaan.SreihaanFood.service.UserService;
import com.sreihaan.SreihaanFood.utils.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest createUserRequest)
    {
        logger.info("[Create User] Create user request initiated for user -> "+ createUserRequest.getEmail()+" role -> "+createUserRequest.getRole());
        if(createUserRequest.getRole() == Role.ADMIN || createUserRequest.getRole() == Role.MODERATOR && !AuthUtil.isCurrentUserIsAdmin())
        {
            logger.info("[Create User] Non admin user trying to create "+createUserRequest.getRole());
            throw new AccessDeniedException("Only Admin is allowed to create Moderator and Admin roles");
        }
        User user = convertCreateUserRequestToUserObject(createUserRequest);
        String password = createUserRequest.getPassword();
        if(password.length() < 8 || !createUserRequest.getConfirmPassword().equals(password))
        {
            logger.info("[Create User] bad password given, user -> "+ createUserRequest.getEmail());
            return ResponseEntity.badRequest().build();
        }
        user = userService.createUser(user, password);
        logger.info("[Create User] User Creation successfull, User -> "+user.getEmail());
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<UserDTO> confirmAccount(@RequestParam("token") String confirmationToken)
    {
        logger.info("[Confirm User] confirm account initiated, confirmation token -> "+ confirmationToken);
        User user = userService.confirmUser(confirmationToken);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    private User convertCreateUserRequestToUserObject(CreateUserRequest createUserRequest)
    {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        Role userRole = createUserRequest.getRole();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.USER);
        if(userRole == Role.MODERATOR || userRole == Role.ADMIN)
        {
            userRoles.add(Role.MODERATOR);
        }
        if(userRole == Role.ADMIN)
        {
            userRoles.add(Role.ADMIN);
        }
        user.setRoles(userRoles);
        return user;
    }

    @GetMapping()
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email)
    {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

}
