package br.com.simplified_elo_payment.account.application.dto.transaction;

public record TransactionResult(String receiverCurrentBalance, String payerCurrentBalance) {}