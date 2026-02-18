package br.com.simplified_elo_payment.account.application.service;

import br.com.simplified_elo_payment.account.application.dto.AccountServiceResponseDto;
import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import br.com.simplified_elo_payment.account.infrastructure.dto.PaymentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    public AccountServiceResponseDto transaction(String paidAmount, Long receivingUserId, Long payingUserId) {
        BigDecimal convertedPaidAmount = new BigDecimal(paidAmount);

        AccountEntity foundReceiver = this.iAccountRepository.findAccountByUserId(receivingUserId);
        AccountEntity foundPayer = this.iAccountRepository.findAccountByUserId(payingUserId);

        foundReceiver.setBalance(foundReceiver.getBalance().add(convertedPaidAmount));
        foundPayer.setBalance(foundPayer.getBalance().subtract(convertedPaidAmount));

        PaymentResponseDto response = this.iAccountRepository.paymentTransaction(foundReceiver, foundPayer);

        String responseText = new String("Receiver: " + response.receiver().getBalance() + " | Payer: " + response.payer().getBalance());

        return new AccountServiceResponseDto(responseText);
    }

    public Long createNewAccount(String initialBalance, Long userId, Set<String> paymentTypes) {
        BigDecimal convertedInitialBalance = new BigDecimal(initialBalance);
        Set<PaymentType> ConvertedPaymentTypes = paymentTypes.stream()
                .map(type -> PaymentType.valueOf(type.toUpperCase()))
                .collect(Collectors.toSet());
        return this.iAccountRepository.createNewAccount(convertedInitialBalance, userId, ConvertedPaymentTypes);
    }
}