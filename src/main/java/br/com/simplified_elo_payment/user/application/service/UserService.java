package br.com.simplified_elo_payment.user.application.service;

import br.com.simplified_elo_payment.user.application.dto.CreateUserCommand;
import br.com.simplified_elo_payment.user.application.dto.UserServiceResponseDto;
import br.com.simplified_elo_payment.user.domain.entity.UserEntity;
import br.com.simplified_elo_payment.user.domain.port.IPortAccountProvider;
import br.com.simplified_elo_payment.user.domain.repository.IUserRepository;
import br.com.simplified_elo_payment.user.domain.valueobject.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IPortAccountProvider iPortAccountProvider;

    public UserServiceResponseDto createUser(CreateUserCommand createUserCommand) {
        UserEntity userEntity = new UserEntity(createUserCommand.username(), createUserCommand.email(), createUserCommand.password(), UserRole.valueOf(createUserCommand.role()));

        UserEntity response = this.iUserRepository.createUser(userEntity);

        this.iPortAccountProvider.creationAccountEvent(response.getId(), new BigDecimal(100));

        return new UserServiceResponseDto(response.getId(), response.getUsername(), response.getEmail());
    }
}