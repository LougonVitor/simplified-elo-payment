package br.com.simplified_elo_payment.user.api.controller;

import br.com.simplified_elo_payment.user.api.dto.UserRequestDto;
import br.com.simplified_elo_payment.user.api.dto.UserResponseDto;
import br.com.simplified_elo_payment.user.api.mapper.UserMapper;
import br.com.simplified_elo_payment.user.application.dto.CreateUserCommand;
import br.com.simplified_elo_payment.user.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto request) {
        CreateUserCommand userCommand = UserMapper.toCreateUserCommand(request);

        return UserMapper.toResponseDto(this.userService.createUser(userCommand));
    }
}