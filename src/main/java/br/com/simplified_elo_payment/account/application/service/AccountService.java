package br.com.simplified_elo_payment.account.application.service;

import br.com.simplified_elo_payment.account.application.dto.AccountServiceResponseDto;
import br.com.simplified_elo_payment.account.application.exceptions.InsufficientBalanceException;
import br.com.simplified_elo_payment.account.application.exceptions.PaymentTypeNotAcceptedException;
import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import br.com.simplified_elo_payment.account.infrastructure.dto.PaymentResponseDto;
import br.com.simplified_elo_payment.common.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository iAccountRepository;
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    public AccountServiceResponseDto transaction(String paidAmount, Long receivingUserId, Long payingUserId, String paymentType) {
        BigDecimal convertedPaidAmount = new BigDecimal(paidAmount);

        //Searching for receiver and payer, if is not founded, throws an exception
        AccountEntity foundReceiver = this.iAccountRepository.findAccountByUserId(receivingUserId);
        AccountEntity foundPayer = this.iAccountRepository.findAccountByUserId(payingUserId);

        log.info("Processing transaction: Payer {} -> Receiver {} | Amount: {}", payingUserId, receivingUserId, paidAmount);

        //Validating all logic
        validateUserExistence(foundReceiver, foundPayer);
        validateBalance(convertedPaidAmount, foundPayer.getBalance());
        validatePaymentType(paymentType, foundReceiver);

        //The transaction logic
        foundReceiver.deposit(convertedPaidAmount);
        foundPayer.withdraw(convertedPaidAmount);

        PaymentResponseDto response = this.iAccountRepository.transaction(foundReceiver, foundPayer);

        //Structuring the response
        return new AccountServiceResponseDto("Receiver: " + response.receiver().getBalance() + " | Payer: " + response.payer().getBalance());
    }

    public Long createNewAccount(String initialBalance, Long userId, Set<String> paymentTypes) {
        BigDecimal convertedInitialBalance = new BigDecimal(initialBalance);
        Set<PaymentType> ConvertedPaymentTypes = paymentTypes.stream()
                .map(type -> PaymentType.valueOf(type.toUpperCase()))
                .collect(Collectors.toSet());

        log.info("Processing account creation: InitialBalance {} -> IdCreated {}", initialBalance, userId);

        return this.iAccountRepository.createNewAccount(convertedInitialBalance, userId, ConvertedPaymentTypes);
    }

    public void validateUserExistence(AccountEntity receiver, AccountEntity payer) {
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
        if(paidAmount.compareTo(payerBalance) > 0) {
            log.error("Handling insufficient balance exception: Amount {} -> Paid {}", paidAmount, payerBalance);
            throw new InsufficientBalanceException("Insufficient balance!\nBalance: " + payerBalance);
        }
    }

    public void validatePaymentType(String paymentType, AccountEntity receiver) {
        if(!receiver.getPaymentType().contains(PaymentType.valueOf(paymentType))) {
            log.error("Handling payment type exception: Type Required {}", paymentType);
            throw new PaymentTypeNotAcceptedException("The receiver cannot accept this payment type");
        }
    }
}