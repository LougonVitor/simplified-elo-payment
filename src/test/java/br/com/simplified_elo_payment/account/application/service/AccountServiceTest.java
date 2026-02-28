package br.com.simplified_elo_payment.account.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.simplified_elo_payment.account.application.dto.creation.PerformCreationCommand;
import br.com.simplified_elo_payment.account.application.dto.transaction.PerformTransactionCommand;
import br.com.simplified_elo_payment.account.application.dto.transaction.TransactionResult;
import br.com.simplified_elo_payment.account.domain.exceptions.InsufficientBalanceException;
import br.com.simplified_elo_payment.account.domain.exceptions.PaymentTypeNotAcceptedException;
import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.domain.repository.IAccountRepository;
import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;
import br.com.simplified_elo_payment.account.infrastructure.dto.PaymentResponseDto;
import br.com.simplified_elo_payment.common.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private IAccountRepository iAccountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @InjectMocks
    private CreationService creationService;

    private AccountEntity payer;
    private AccountEntity receiver;
    private PaymentType paymentTypes;

    @BeforeEach
    void setUp() {
        //
        //Basic data for transaction testing.
        payer = new AccountEntity(1L, new BigDecimal("100.00"));
        payer.setId(10L);

        receiver = new AccountEntity(2L, new BigDecimal("50.00"));
        receiver.setId(20L);
    }

    // --- TRANSACTION TESTS (MONEY TRANSFER) ---

    @Test
    @DisplayName("Should throw PaymentTypeNotAccepted when when receiver does not accept the payment type")
    void transactionUnsuccessful() {
        receiver.setBalance(new BigDecimal("100.00"));
        payer.setBalance(new BigDecimal("100.00"));
        receiver.setPaymentType(Set.of(PaymentType.ELO));

        when(iAccountRepository.findAccountByUserId(1L)).thenReturn(payer);
        when(iAccountRepository.findAccountByUserId(2L)).thenReturn(receiver);

        var command = new PerformTransactionCommand("100.00", receiver.getUserId(), payer.getUserId(), "MASTERCARD");

        assertThrows(PaymentTypeNotAcceptedException.class, () -> {
            this.transactionService.executeTransaction(command);
        });

        verify(iAccountRepository, never()).executeTransaction(any(), any());
    }

    @Test
    @DisplayName("Should transfer money successfully when balance is sufficient")
    void transactionSuccess() {
        Set<PaymentType> acceptedPaymentTypes = Set.of(PaymentType.ELO);

        receiver.setBalance(new BigDecimal("100.00"));
        payer.setBalance(new BigDecimal("100.00"));

        receiver.setPaymentType(acceptedPaymentTypes);

        when(iAccountRepository.findAccountByUserId(1L)).thenReturn(payer);
        when(iAccountRepository.findAccountByUserId(2L)).thenReturn(receiver);

        var command = new PerformTransactionCommand("30.00", receiver.getUserId(), payer.getUserId(), "ELO");

        TransactionResult result = transactionService.executeTransaction(command);

        assertNotNull(result);
        assertEquals(new BigDecimal("130.00"), new BigDecimal(result.receiverCurrentBalance()));
        assertEquals(new BigDecimal("70.00"), payer.getBalance());
        assertEquals(new BigDecimal("130.00"), receiver.getBalance());
        verify(iAccountRepository).executeTransaction(receiver, payer);
    }

    @Test
    @DisplayName("Should throw InsufficientBalanceException when payer balance is low")
    void transactionInsufficientBalance() {
        receiver.setBalance(new BigDecimal("100.00"));
        payer.setBalance(new BigDecimal("100.00"));

        when(iAccountRepository.findAccountByUserId(1L)).thenReturn(payer);
        when(iAccountRepository.findAccountByUserId(2L)).thenReturn(receiver);

        var command = new PerformTransactionCommand("200.00", receiver.getUserId(), payer.getUserId(), "ELO");

        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.executeTransaction(command);
        });

        verify(iAccountRepository, never()).executeTransaction(any(), any());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when Payer is not found")
    void shouldThrowUserNotFoundExceptionWhenPayerIsNull() {
        when(iAccountRepository.findAccountByUserId(1L)).thenReturn(null);
        when(iAccountRepository.findAccountByUserId(2L)).thenReturn(receiver);

        var command = new PerformTransactionCommand("10.00", receiver.getUserId(), payer.getUserId(), "ELO");

        assertThrows(UserNotFoundException.class, () -> {
            transactionService.executeTransaction(command);
        });
    }

    // --- ACCOUNT CREATION TESTS ---

    @Test
    @DisplayName("Should create account with correctly mapped payment types")
    void createNewAccountSuccess() {
        BigDecimal initialBalance = new BigDecimal("150.00");
        Long userId = 100L;
        Set<String> paymentTypesInput = Set.of("ELO", "visa");

        when(iAccountRepository.createAccount(any(BigDecimal.class), eq(userId), anySet()))
                .thenReturn(1L);

        var command = new PerformCreationCommand(initialBalance, userId, paymentTypesInput);

        Long resultId = creationService.createAccount(command);

        assertEquals(1L, resultId);
        verify(iAccountRepository).createAccount(
                argThat(balance -> balance.compareTo(new BigDecimal("150.00")) == 0),
                eq(userId),
                argThat(types -> types.contains(PaymentType.ELO) && types.contains(PaymentType.VISA))
        );
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when payment type is invalid")
    void createNewAccountInvalidEnum() {
        Set<String> invalidTypes = Set.of("BITCOIN");

        var command = new PerformCreationCommand(new BigDecimal("100.00"), 1L, invalidTypes);

        assertThrows(IllegalArgumentException.class, () -> {
            creationService.createAccount(command);
        });

        verify(iAccountRepository, never()).createAccount(any(), any(), any());
    }
}