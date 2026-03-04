package br.com.simplified_elo_payment.user.application.service;

import br.com.simplified_elo_payment.user.application.dto.auth.AuthenticationUserCommand;
import br.com.simplified_elo_payment.common.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String loginAuthentication(AuthenticationUserCommand command) {
        return generateToken(command);
    }

    private String generateToken(AuthenticationUserCommand command) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(command.username(), command.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return this.tokenService.generateToken((User) auth.getPrincipal());
    }
}