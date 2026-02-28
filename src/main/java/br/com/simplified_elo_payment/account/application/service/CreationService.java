package br.com.simplified_elo_payment.account.application.service;

import br.com.simplified_elo_payment.account.application.dto.creation.PerformCreationCommand;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Service
public class CreationService {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Transactional
    public Long createAccount(PerformCreationCommand command) {
        this.logCreation(command.initialBalance(), command.userId());

        return this.persist(command.initialBalance(), command.userId(), command.getPaymentTypesAsEnum());
    }

    private void logCreation(BigDecimal initialBalance, Long userId) {
        log.info("Processing account creation: InitialBalance {} -> IdCreated {}", initialBalance, userId);
    }

    private Long persist(BigDecimal amount, Long userId, Set<PaymentType> paymentType) {
        return this.iAccountRepository.createAccount(amount, userId, paymentType);
    }
}