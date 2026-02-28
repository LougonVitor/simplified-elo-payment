package br.com.simplified_elo_payment.account.infrastructure.repository;

import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import br.com.simplified_elo_payment.account.infrastructure.entity.AccountJpaEntity;
import br.com.simplified_elo_payment.account.infrastructure.mapper.AccountInfraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public class AccountJpaAdapter implements IAccountRepository {
    @Autowired
    private AccountJpaRepository accountJpaRepository;

    @Override
    public void executeTransaction(AccountEntity receiver, AccountEntity payer) {
        this.updateAccountBalance(receiver);
        this.updateAccountBalance(payer);
    }

    @Override
    public AccountEntity findAccountByUserId(Long userId) {
        AccountJpaEntity response = this.accountJpaRepository.findByUserId(userId).orElse(null);
                //.orElseThrow(() -> new RuntimeException("User not found by ID: " + userId));
        if(response == null) return null;
        return AccountInfraMapper.toDomainEntity(response);
    }

    @Override
    public AccountEntity updateAccountBalance(AccountEntity accountUpdated) {
        AccountJpaEntity response = AccountInfraMapper.toJpaEntity(accountUpdated);
        this.accountJpaRepository.save(response);
        return AccountInfraMapper.toDomainEntity(response);
    }

    @Override
    public Long createAccount(BigDecimal initialValue, Long userId, Set<PaymentType> paymentTypes) {
        AccountJpaEntity response = this.accountJpaRepository.save(new AccountJpaEntity(userId, initialValue, paymentTypes));
        AccountEntity mappedResponse = AccountInfraMapper.toDomainEntity(response);
        return mappedResponse.getUserId();
    }
}