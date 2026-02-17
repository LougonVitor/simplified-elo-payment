package br.com.simplified_elo_payment.user.application.service;

import br.com.simplified_elo_payment.user.application.dto.CreateUserComand;
import br.com.simplified_elo_payment.user.application.dto.UserServiceResponseDto;
import br.com.simplified_elo_payment.user.domain.entity.UserEntity;
import br.com.simplified_elo_payment.user.domain.repository.IUserRepository;
import br.com.simplified_elo_payment.user.domain.valueobject.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IUserRepository iUserRepository;

    public UserServiceResponseDto createUser(CreateUserComand createUserComand) {
        UserEntity userEntity = new UserEntity(createUserComand.username(),createUserComand.email(), createUserComand.password(), UserRole.valueOf(createUserComand.role()));

        UserEntity response = this.iUserRepository.createUser(userEntity);

        return new UserServiceResponseDto(response.getId(), response.getUsername(), response.getEmail());
    }
}