package br.com.simplified_elo_payment.account.application.service;

import br.com.simplified_elo_payment.account.application.dto.AccountServiceResponseDto;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    public AccountServiceResponseDto paymentTransaction(BigDecimal paidAmount, Long receivingUserId) {
        BigDecimal newBalance = this.iAccountRepository.paymentTransaction(paidAmount, receivingUserId);
        return new AccountServiceResponseDto(newBalance);
    }

    public AccountServiceResponseDto receiptTransaction(BigDecimal receivedAmount, Long payingUserId) {
        BigDecimal newBalance = this.iAccountRepository.receiptTransaction(receivedAmount, payingUserId);
        return new AccountServiceResponseDto(newBalance);
    }

    public AccountServiceResponseDto createNewAccount(Long userId, BigDecimal initialBalance) {
        this.iAccountRepository.
    }
}