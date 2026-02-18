package br.com.simplified_elo_payment.user.api.mapper;

import br.com.simplified_elo_payment.user.api.dto.UserRequestDto;
import br.com.simplified_elo_payment.user.application.dto.CreateUserCommand;
import br.com.simplified_elo_payment.user.api.dto.UserResponseDto;
import br.com.simplified_elo_payment.user.application.dto.UserServiceResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static CreateUserCommand toCreateUserCommand(UserRequestDto request) {
        return new CreateUserCommand(
                request.username(),
                request.email(),
                request.password(),
                request.role(),
                request.initialBalance(),
                request.paymentTypes()
        );
    }

    public static UserResponseDto toResponseDto(UserServiceResponseDto serviceResponseDto) {
        return new UserResponseDto(serviceResponseDto.id(), serviceResponseDto.username(), serviceResponseDto.email());
    }
}