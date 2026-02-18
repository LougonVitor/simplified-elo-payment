package br.com.simplified_elo_payment.account.application.exceptions;

public class PaymentTypeNotAcceptedException extends RuntimeException {
    public PaymentTypeNotAcceptedException(String message) {
        super(message);
    }
}