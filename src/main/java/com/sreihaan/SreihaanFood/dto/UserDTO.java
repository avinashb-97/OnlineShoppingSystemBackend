package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Role;
import com.sreihaan.SreihaanFood.model.persistence.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class UserDTO {

    public String firstName;

    public String lastName;

    public String email;

    public Role role;

    public static UserDTO convertEntityToUserDTO(User user)
    {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        Role role = Role.USER;
        if(user.getRoles().contains(Role.ADMIN))
        {
            role = Role.ADMIN;
        }
        else if(user.getRoles().contains(Role.MODERATOR))
        {
            role = Role.MODERATOR;
        }
        userDTO.setRole(role);
        return userDTO;
    }
}
