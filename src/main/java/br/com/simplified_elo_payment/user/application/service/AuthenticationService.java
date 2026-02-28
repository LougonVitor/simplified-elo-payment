package br.com.simplified_elo_payment.user.application.service;

import br.com.simplified_elo_payment.user.application.dto.CreateUserCommand;
import br.com.simplified_elo_payment.user.application.dto.UserServiceResponseDto;
import br.com.simplified_elo_payment.user.application.dto.auth.AuthenticationUserCommand;
import br.com.simplified_elo_payment.user.domain.entity.UserEntity;
import br.com.simplified_elo_payment.user.domain.port.IPortAccountProvider;
import br.com.simplified_elo_payment.user.domain.repository.IUserRepository;
import br.com.simplified_elo_payment.user.domain.valueobject.UserRole;
import br.com.simplified_elo_payment.common.security.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthenticationService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPortAccountProvider iPortAccountProvider;

    public String loginAuthentication(AuthenticationUserCommand data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token =  this.tokenService.generateToken((User) auth.getPrincipal());

        System.out.println(token);

        return token;
    }

    @Transactional
    public UserServiceResponseDto createUser(CreateUserCommand createUserCommand) throws Exception {
        if(this.userRepository.findByUsername(createUserCommand.username()) != null) throw new Exception("User already exists!");

        String encryptedPassword = new BCryptPasswordEncoder().encode(createUserCommand.password());

        UserEntity userToCreate = new UserEntity(createUserCommand.username(), createUserCommand.email(), encryptedPassword, UserRole.valueOf(createUserCommand.role()));

        UserEntity response = this.userRepository.createUser(userToCreate);

        this.iPortAccountProvider.creationAccountEvent(response.getId(), new BigDecimal(createUserCommand.initialBalance()), createUserCommand.paymentTypes());

        return new UserServiceResponseDto(response.getId(), response.getUsername(), response.getEmail());
    }
}