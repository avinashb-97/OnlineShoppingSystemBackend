package com.sreihaan.SreihaanFood.conf;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = null;
        if (authentication != null && authentication.isAuthenticated()) {
            name = authentication.getName();
        }
        Optional<String> optName = Optional.of(name);
        return optName;
    }

}
