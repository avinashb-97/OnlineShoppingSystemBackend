package com.sreihaan.SreihaanFood.model.persistence;


import com.sreihaan.SreihaanFood.model.persistence.enums.Role;
import io.github.kaiso.relmongo.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Getter
@Setter
@Document(collection = "user")
public class User implements Persistable<Long> {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String salt;

    private Set<Role> roles;

    private boolean accountNonLocked = true;

    private boolean enabled;

    private boolean persisted;

    @CreatedDate
    private Date createdTime = new Date();

    @LastModifiedDate
    private Date lastModifiedTime;

    @OneToOne(mappedBy = "user")
    private UserToken userToken;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinProperty(name = "orders")
    private List<Order> orders;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }
}
