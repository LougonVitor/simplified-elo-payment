package br.com.simplified_elo_payment.user.api.mapper;

import br.com.simplified_elo_payment.user.api.dto.auth.AuthenticationRequestDto;
import br.com.simplified_elo_payment.user.api.dto.auth.CreateRequestDto;
import br.com.simplified_elo_payment.user.application.dto.CreateUserCommand;
import br.com.simplified_elo_payment.user.application.dto.auth.AuthenticationUserCommand;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {
    /**
     * Request Mapper
     * Map the AuthRequestDto from API to AuthenticationUserCommand from APPLICATION
     */
    public static AuthenticationUserCommand toAuthenticationUserCommand(AuthenticationRequestDto requestDto) {
        return new AuthenticationUserCommand(requestDto.username(), requestDto.password());
    }

    /**
     * Request Mapper
     * Map the RegisterRequestDto from API to CreateUserCommand from APPLICATION
     */
    public static CreateUserCommand toCreateUserCommand(CreateRequestDto request) {
        return new CreateUserCommand(request.username(), request.email(), request.password(), request.role(), request.initialBalance(), request.paymentTypes());
    }
}