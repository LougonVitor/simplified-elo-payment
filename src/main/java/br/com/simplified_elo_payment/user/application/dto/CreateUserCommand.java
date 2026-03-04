package br.com.simplified_elo_payment.user.application.dto;

import br.com.simplified_elo_payment.user.domain.valueobject.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Set;

public record CreateUserCommand(String username, String email, String password, String role, String initialBalance, Set<String> paymentTypes) {
    public UserRole getRoleAsEnum() {
        return UserRole.valueOf(role);
    }

    public String getEncryptedPassword() {
        return new BCryptPasswordEncoder().encode(password);
    }

    public BigDecimal getBalanceAsBigDecimal() {
        return new BigDecimal(initialBalance);
    }
}