package com.sreihaan.SreihaanFood.model.persistence.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    MODERATOR,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
