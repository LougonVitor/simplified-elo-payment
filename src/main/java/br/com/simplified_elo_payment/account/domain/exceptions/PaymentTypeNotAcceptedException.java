package br.com.simplified_elo_payment.account.domain.exceptions;

public class PaymentTypeNotAcceptedException extends RuntimeException {
    public PaymentTypeNotAcceptedException(String message) {
        super(message);
    }
}