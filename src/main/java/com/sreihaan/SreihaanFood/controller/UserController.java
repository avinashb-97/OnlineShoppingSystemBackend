package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.OtpDTO;
import com.sreihaan.SreihaanFood.dto.UserDTO;
import com.sreihaan.SreihaanFood.exception.InvalidOTPException;
import com.sreihaan.SreihaanFood.model.persistence.enums.Role;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.requests.ChangePasswordRequest;
import com.sreihaan.SreihaanFood.model.requests.CreateUserRequest;
import com.sreihaan.SreihaanFood.model.requests.PasswordResetRequest;
import com.sreihaan.SreihaanFood.service.EmailSenderService;
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

    @Autowired
    private EmailSenderService emailSenderService;

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
        boolean isOTPVerified = userService.verifyOTP(createUserRequest.getEmail(), createUserRequest.getOtp());
        User user = convertCreateUserRequestToUserObject(createUserRequest);
        String password = createUserRequest.getPassword();
        if(!isOTPVerified)
        {
            logger.info("[Create User] Invalid OTP, email -> "+user.getEmail());
            throw new InvalidOTPException("OTP is invalid or is Expired");
        }
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

    @GetMapping()
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email)
    {
        if(!AuthUtil.isCurrentUserIsAdmin())
        {
            throw new AccessDeniedException("Access denied to get user");
        }
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam String email)
    {
        userService.deleteUser(email);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<UserDTO> confirmAccount(@RequestParam("token") String confirmationToken)
    {
        logger.info("[Confirm User] confirm account initiated, confirmation token -> "+ confirmationToken);
        User user = userService.confirmUser(confirmationToken);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    @PostMapping("/password/reset")
    public ResponseEntity resetPassword(@RequestBody PasswordResetRequest passwordResetRequest)
    {
        String email = passwordResetRequest.getEmail();
        String password = passwordResetRequest.getPassword();
        logger.info("[Reset password] reset password initiated, email -> "+ email);
        if(!isPasswordStrong(password, passwordResetRequest.getConfirmPassword()))
        {
            logger.info("[Reset Password] bad password given, email -> "+ email);
            return ResponseEntity.badRequest().build();
        }
        boolean isOTPVerified = userService.verifyOTP(passwordResetRequest.getEmail(), passwordResetRequest.getOtp());
        if(!isOTPVerified)
        {
            logger.info("[Create User] Invalid OTP, email -> "+email);
            throw new InvalidOTPException("OTP is invalid or is Expired");
        }
        userService.resetPassword(email, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/change")
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

    @PostMapping("/otp/generate")
    public void sendOTPToVerifyGuestEmail(@RequestBody OtpDTO otpDTO)
    {
        String email = otpDTO.getEmail();
        userService.generateOTP(email);
    }

    @PostMapping("/otp/verify")
    public ResponseEntity confirmOTP(@RequestBody OtpDTO otpDTO)
    {
        boolean isVerified = userService.verifyOTP(otpDTO.getEmail(), otpDTO.getOtp());
        if(isVerified)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Invalid OTP");
    }

}
