package br.com.simplified_elo_payment.account.infrastructure.repository;

import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.infrastructure.entity.AccountJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class AccountJpaAdapter implements IAccountRepository {
    @Autowired
    private AccountJpaRepository accountJpaRepository;

    @Override
    public BigDecimal paymentTransaction(BigDecimal paidAmount, Long receivingUserId) {
        AccountEntity foundAccount = this.findAccountByUserId(receivingUserId);
        foundAccount.setBalance(foundAccount.getBalance().add(paidAmount));
        return this.updateAccountBalance(foundAccount);
    }

    @Override
    public BigDecimal receiptTransaction(BigDecimal receivedAmount, Long payingUserId) {
        AccountEntity foundAccount = this.findAccountByUserId(payingUserId);
        foundAccount.setBalance(foundAccount.getBalance().subtract(receivedAmount));
        return this.updateAccountBalance(foundAccount);
    }

    @Override
    public AccountEntity findAccountByUserId(Long userId) {
        AccountJpaEntity accountJpaEntity = accountJpaRepository.findByUserId(userId);
        return new AccountEntity(accountJpaEntity.getId(), accountJpaEntity.getUserId(), accountJpaEntity.getBalance());
    }

    @Override
    public BigDecimal updateAccountBalance(AccountEntity accountUpdated) {
        return this.accountJpaRepository.save(new AccountJpaEntity(accountUpdated.getId(),accountUpdated.getUserId(), accountUpdated.getBalance())).getBalance();
    }
}