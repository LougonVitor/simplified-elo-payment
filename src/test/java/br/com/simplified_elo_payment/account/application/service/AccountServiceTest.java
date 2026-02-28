package br.com.simplified_elo_payment.account.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.simplified_elo_payment.account.application.dto.TransactionResult;
import br.com.simplified_elo_payment.account.application.exceptions.InsufficientBalanceException;
import br.com.simplified_elo_payment.account.application.exceptions.PaymentTypeNotAcceptedException;
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
    private AccountService accountService;

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

        when(iAccountRepository.findAccountByUserId(1L)).thenReturn(receiver);
        when(iAccountRepository.findAccountByUserId(2L)).thenReturn(payer);

        assertThrows(PaymentTypeNotAcceptedException.class, () -> {
            this.accountService.transaction("100.00", 1L, 2L, "MASTERCARD");
        });

        verify(iAccountRepository, never()).transaction(any(), any());
    }

    @Test
    @DisplayName("Should transfer money successfully when balance is sufficient")
    void transactionSuccess() {
        Set<PaymentType> acceptedPaymentTypes = Set.of(PaymentType.ELO);

        receiver.setBalance(new BigDecimal("100.00"));
        payer.setBalance(new BigDecimal("100.00"));

        receiver.setPaymentType(acceptedPaymentTypes);
        receiver.setPaymentType(acceptedPaymentTypes);

        when(iAccountRepository.findAccountByUserId(1L)).thenReturn(receiver);
        when(iAccountRepository.findAccountByUserId(2L)).thenReturn(payer);

        PaymentResponseDto mockResponse = new PaymentResponseDto(receiver, payer);
        when(iAccountRepository.transaction(any(), any())).thenReturn(mockResponse);

        TransactionResult result = accountService.transaction("30.00", 1L, 2L, "ELO");

        assertNotNull(result);
        assertTrue(result.response().contains("Receiver: 130.00"));
        assertTrue(result.response().contains("Payer: 70.00"));
        verify(iAccountRepository, times(1)).transaction(any(), any());
    }

    @Test
    @DisplayName("Should throw InsufficientBalanceException when payer balance is low")
    void transactionInsufficientBalance() {
        receiver.setBalance(new BigDecimal("100.00"));
        payer.setBalance(new BigDecimal("100.00"));

        when(iAccountRepository.findAccountByUserId(1L)).thenReturn(receiver);
        when(iAccountRepository.findAccountByUserId(2L)).thenReturn(payer);

        assertThrows(InsufficientBalanceException.class, () -> {
            accountService.transaction("200.00", 1L, 2L, "ELO");
        });

        verify(iAccountRepository, never()).transaction(any(), any());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when Payer is not found")
    void shouldThrowUserNotFoundExceptionWhenPayerIsNull() {
        when(iAccountRepository.findAccountByUserId(2L)).thenReturn(null);
        when(iAccountRepository.findAccountByUserId(1L)).thenReturn(receiver);

        assertThrows(UserNotFoundException.class, () -> {
            accountService.transaction("10.00", 1L, 2L, "ELO");
        });
    }

    // --- ACCOUNT CREATION TESTS ---

    @Test
    @DisplayName("Should create account with correctly mapped payment types")
    void createNewAccountSuccess() {
        String initialBalance = "150.00";
        Long userId = 100L;
        Set<String> paymentTypesInput = Set.of("ELO", "visa");

        when(iAccountRepository.createNewAccount(any(BigDecimal.class), eq(userId), anySet()))
                .thenReturn(1L);

        Long resultId = accountService.createNewAccount(initialBalance, userId, paymentTypesInput);

        assertEquals(1L, resultId);
        verify(iAccountRepository).createNewAccount(
                argThat(balance -> balance.compareTo(new BigDecimal("150.00")) == 0),
                eq(userId),
                argThat(types -> types.contains(PaymentType.ELO) && types.contains(PaymentType.VISA))
        );
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when payment type is invalid")
    void createNewAccountInvalidEnum() {
        Set<String> invalidTypes = Set.of("BITCOIN");

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.createNewAccount("100.00", 1L, invalidTypes);
        });

        verify(iAccountRepository, never()).createNewAccount(any(), any(), any());
    }
}