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

    public AccountServiceResponseDto paymentTransaction(String paidAmount, Long receivingUserId) {
        BigDecimal convertedPaidAmount = new BigDecimal(paidAmount);
        BigDecimal newBalance = this.iAccountRepository.paymentTransaction(convertedPaidAmount, receivingUserId);
        return new AccountServiceResponseDto(newBalance);
    }

    public AccountServiceResponseDto receiptTransaction(String receivedAmount, Long payingUserId) {
        BigDecimal convertedReceivedAmount = new BigDecimal(receivedAmount);
        BigDecimal newBalance = this.iAccountRepository.receiptTransaction(convertedReceivedAmount, payingUserId);
        return new AccountServiceResponseDto(newBalance);
    }

    public Long createNewAccount(String initialBalance, Long userId) {
        BigDecimal convertedInitialBalance = new BigDecimal(initialBalance);
        return this.iAccountRepository.createNewAccount(convertedInitialBalance, userId);
    }
}