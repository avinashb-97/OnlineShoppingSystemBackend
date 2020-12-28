package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.UserDTO;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.requests.CreateUserRequest;
import com.sreihaan.SreihaanFood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest createUserRequest)
    {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        String password = createUserRequest.getPassword();
        if(password.length() < 8 || !createUserRequest.getConfirmPassword().equals(password))
        {
            return ResponseEntity.badRequest().build();
        }
        user = userService.createUser(user,password);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

    @GetMapping()
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email)
    {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserDTO.convertEntityToUserDTO(user));
    }

}
