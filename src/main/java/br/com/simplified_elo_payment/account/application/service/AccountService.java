package br.com.simplified_elo_payment.account.application.service;

import br.com.simplified_elo_payment.account.application.dto.AccountServiceResponseDto;
import br.com.simplified_elo_payment.account.application.exceptions.InsufficientBalanceException;
import br.com.simplified_elo_payment.account.application.exceptions.PaymentTypeNotAcceptedException;
import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import br.com.simplified_elo_payment.account.infrastructure.dto.PaymentResponseDto;
import br.com.simplified_elo_payment.account.infrastructure.entity.AccountJpaEntity;
import br.com.simplified_elo_payment.common.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    public AccountServiceResponseDto transaction(String paidAmount, Long receivingUserId, Long payingUserId, String paymentType) {
        BigDecimal convertedPaidAmount = new BigDecimal(paidAmount);

        //Searching for receiver and payer, if is not founded, throws an exception
        AccountJpaEntity foundReceiver = this.iAccountRepository.findAccountByUserId(receivingUserId);
        AccountJpaEntity foundPayer = this.iAccountRepository.findAccountByUserId(payingUserId);

        //Validating all logic
        validateTransaction(foundReceiver, foundPayer);
        validateBalance(convertedPaidAmount, foundPayer.getBalance());
        validatePaymentType(paymentType, foundReceiver);

        //The transaction logic
        foundReceiver.setBalance(foundReceiver.getBalance().add(convertedPaidAmount));
        foundPayer.setBalance(foundPayer.getBalance().subtract(convertedPaidAmount));

        AccountEntity foundReceiverEntity = new AccountEntity(foundReceiver.getId(), foundReceiver.getUserId(), foundReceiver.getBalance(), foundReceiver.getPaymentTypesAccepted());
        AccountEntity foundPayerEntity = new AccountEntity(foundPayer.getId(), foundPayer.getUserId(), foundPayer.getBalance(), foundPayer.getPaymentTypesAccepted());

        PaymentResponseDto response = this.iAccountRepository.transaction(foundReceiverEntity, foundPayerEntity);

        //Structuring the response
        return new AccountServiceResponseDto("Receiver: " + response.receiver().getBalance() + " | Payer: " + response.payer().getBalance());
    }

    public Long createNewAccount(String initialBalance, Long userId, Set<String> paymentTypes) {
        BigDecimal convertedInitialBalance = new BigDecimal(initialBalance);
        Set<PaymentType> ConvertedPaymentTypes = paymentTypes.stream()
                .map(type -> PaymentType.valueOf(type.toUpperCase()))
                .collect(Collectors.toSet());
        return this.iAccountRepository.createNewAccount(convertedInitialBalance, userId, ConvertedPaymentTypes);
    }

    public void validateTransaction(AccountJpaEntity receiver, AccountJpaEntity payer) {
        if(receiver == null && payer == null) {
            throw new UserNotFoundException("Payer and Receiver not found!");
        } else {
            if (receiver == null) {
                throw new UserNotFoundException("Receiver not found!");
            }
            if (payer == null) {
                throw new UserNotFoundException("Payer not found!");
            }
        }
    }

    public void validateBalance(BigDecimal paidAmount, BigDecimal payerBalance) {
        if(paidAmount.compareTo(payerBalance) > 0) throw new InsufficientBalanceException("Insufficient balance!\nBalance: " + payerBalance);
    }

    public void validatePaymentType(String paymentType, AccountJpaEntity receiver) {
        if(!receiver.getPaymentTypesAccepted().contains(PaymentType.valueOf(paymentType)))
            throw new PaymentTypeNotAcceptedException("The receiver cannot accept this payment type");
    }
}