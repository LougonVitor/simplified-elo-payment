package br.com.simplified_elo_payment.account.application.service;

import br.com.simplified_elo_payment.account.application.dto.transaction.PerformTransactionCommand;
import br.com.simplified_elo_payment.account.application.dto.transaction.TransactionResult;
import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.common.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class TransactionService {
    @Autowired
    private IAccountRepository iAccountRepository;
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Transactional
    public TransactionResult executeTransaction(PerformTransactionCommand command) {
        BigDecimal paidAmount = command.getAmountAsBigDecimal();
        AccountEntity foundReceiver = this.findAccountByUserId(command.receivingUserID());
        AccountEntity foundPayer = this.findAccountByUserId(command.payingUserId());

        this.logTransaction(command);

        //Validating all logic
        this.validateUserExistence(foundReceiver, foundPayer);
        foundPayer.canWithdraw(paidAmount);
        foundReceiver.canUsePaymentType(command.paymentType(), foundReceiver.getPaymentType());

        //The transaction logic
        this.executeTransfer(foundReceiver, foundPayer, paidAmount);

        //Structuring the response
        return buildTransactionResult(foundReceiver, foundPayer);
    }

    private void executeTransfer(AccountEntity receiver, AccountEntity payer, BigDecimal amount) {
        receiver.deposit(amount);
        payer.withdraw(amount);

        this.persistTransaction(receiver, payer);
    }

    private void persistTransaction(AccountEntity receiver, AccountEntity payer) {
        this.iAccountRepository.executeTransaction(receiver, payer);
    }

    private AccountEntity findAccountByUserId(Long id) {
        return this.iAccountRepository.findAccountByUserId(id);
    }

    private TransactionResult buildTransactionResult(AccountEntity receiver, AccountEntity payer) {
        return new TransactionResult(
                receiver.getBalance().toString()
                , payer.getBalance().toString()
        );
    }

    private void logTransaction(PerformTransactionCommand command) {
        log.info("Processing transaction: Payer {} -> Receiver {} | Amount: {}", command.payingUserId(), command.receivingUserID(), command.amount());
    }

    private void validateUserExistence(AccountEntity receiver, AccountEntity payer) {
        if(receiver == null && payer == null) throw new UserNotFoundException("Payer and Receiver not found!");
        else if (receiver == null) throw new UserNotFoundException("Receiver not found!");
        else if (payer == null) throw new UserNotFoundException("Payer not found!");
    }
}