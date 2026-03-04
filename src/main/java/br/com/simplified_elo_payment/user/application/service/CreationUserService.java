package br.com.simplified_elo_payment.user.application.service;

import br.com.simplified_elo_payment.user.application.dto.CreateUserCommand;
import br.com.simplified_elo_payment.user.application.dto.UserServiceResponseDto;
import br.com.simplified_elo_payment.user.application.mapper.UserApplicationMapper;
import br.com.simplified_elo_payment.user.domain.entity.UserEntity;
import br.com.simplified_elo_payment.user.domain.port.IPortAccountProvider;
import br.com.simplified_elo_payment.user.domain.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class CreationUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPortAccountProvider iPortAccountProvider;

    @Transactional
    public UserServiceResponseDto createUser(CreateUserCommand command) {
        validateUserExistence(command.username());

        UserEntity response = this.userRepository.createUser(UserApplicationMapper.toConstructedEntity(command));

        callCreationAccountEvent(response.getId(), command.getBalanceAsBigDecimal(), command.paymentTypes());

        return UserApplicationMapper.toResponseDto(response);
    }

    private void validateUserExistence(String username) {
        if(this.userRepository.findByUsername(username) != null) throw new RuntimeException("User already exists!");
    }

    private void callCreationAccountEvent(Long id, BigDecimal initialBalance, Set<String> paymentTypes) {
        this.iPortAccountProvider.creationAccountEvent(id, initialBalance, paymentTypes);
    }
}