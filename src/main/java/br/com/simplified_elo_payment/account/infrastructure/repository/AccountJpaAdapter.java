package br.com.simplified_elo_payment.account.infrastructure.repository;

import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import br.com.simplified_elo_payment.account.infrastructure.dto.PaymentResponseDto;
import br.com.simplified_elo_payment.account.infrastructure.entity.AccountJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public class AccountJpaAdapter implements IAccountRepository {
    @Autowired
    private AccountJpaRepository accountJpaRepository;

    @Override
    public PaymentResponseDto paymentTransaction(AccountEntity receiver, AccountEntity payer) {
        AccountJpaEntity updatedReceiver = this.updateAccountBalance(receiver);
        AccountJpaEntity updatedPayer = this.updateAccountBalance(payer);

        return new PaymentResponseDto(updatedReceiver, updatedPayer);
    }

    @Override
    public AccountEntity findAccountByUserId(Long userId) {
        AccountJpaEntity accountJpaEntity = this.accountJpaRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found by ID!"));
        return new AccountEntity(accountJpaEntity.getId(), accountJpaEntity.getUserId(), accountJpaEntity.getBalance());
    }

    @Override
    public AccountJpaEntity updateAccountBalance(AccountEntity accountUpdated) {
        return this.accountJpaRepository.save(new AccountJpaEntity(accountUpdated.getId(),accountUpdated.getUserId(), accountUpdated.getBalance()));
    }

    @Override
    public Long createNewAccount(BigDecimal initialValue, Long userId, Set<PaymentType> paymentTypes) {
        return this.accountJpaRepository.save(new AccountJpaEntity(userId, initialValue, paymentTypes)).getUserId();
    }
}