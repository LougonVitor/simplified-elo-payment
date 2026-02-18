package br.com.simplified_elo_payment.account.application.service;

import br.com.simplified_elo_payment.account.application.dto.AccountServiceResponseDto;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Long createNewAccount(String initialBalance, Long userId, Set<String> paymentTypes) {
        BigDecimal convertedInitialBalance = new BigDecimal(initialBalance);
        Set<PaymentType> ConvertedPaymentTypes = paymentTypes.stream()
                .map(type -> PaymentType.valueOf(type.toUpperCase()))
                .collect(Collectors.toSet());
        return this.iAccountRepository.createNewAccount(convertedInitialBalance, userId, ConvertedPaymentTypes);
    }
}