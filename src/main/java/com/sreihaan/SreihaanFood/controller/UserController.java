package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.UserDTO;
import com.sreihaan.SreihaanFood.model.persistence.Role;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.requests.ChangePasswordRequest;
import com.sreihaan.SreihaanFood.model.requests.CreateUserRequest;
import com.sreihaan.SreihaanFood.model.requests.PasswordResetRequest;
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
        if(!isPasswordStrong(password, createUserRequest.getConfirmPassword()))
        {
            logger.info("[Create User] bad password given, user -> "+ createUserRequest.getEmail());
            return ResponseEntity.badRequest().build();
        }
        user = userService.createUser(user, password);
        logger.info("[Create User] User Creation successfull, User -> "+user.getEmail());
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    private boolean isPasswordStrong(String password, String confirmPassword)
    {
       return (password.length() >= 8 && confirmPassword.equals(password));
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<UserDTO> confirmAccount(@RequestParam("token") String confirmationToken)
    {
        logger.info("[Confirm User] confirm account initiated, confirmation token -> "+ confirmationToken);
        User user = userService.confirmUser(confirmationToken);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestParam("email") String email)
    {
        logger.info("[Forgot password] forgot password initiated, email -> "+ email);
        userService.forgotPassword(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestBody PasswordResetRequest passwordResetRequest)
    {
        logger.info("[Reset password] reset password initiated, resetToken -> "+ passwordResetRequest.getResetToken());
        String password = passwordResetRequest.getPassword();
        if(!isPasswordStrong(password, passwordResetRequest.getConfirmPassword()))
        {
            logger.info("[Reset Password] bad password given, resetToken -> "+ passwordResetRequest.getResetToken());
            return ResponseEntity.badRequest().build();
        }
        userService.resetPassword(passwordResetRequest.getResetToken(), password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest changePasswordRequest)
    {
        logger.info("[Change password] change password initiated, user -> "+ AuthUtil.getLoggedInUserName());
        String password = changePasswordRequest.getPassword();
        if(!isPasswordStrong(password, changePasswordRequest.getConfirmPassword()))
        {
            logger.info("[Change Password] bad password given, user -> "+ AuthUtil.getLoggedInUserName());
            return ResponseEntity.badRequest().build();
        }
        userService.changePasswordForCurrentUser(changePasswordRequest.getOldPassword() ,password);
        return ResponseEntity.ok().build();

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
