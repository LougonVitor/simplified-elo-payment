package br.com.simplified_elo_payment.account.application.dto;

public record TransactionResult(String receiverCurrentBalance, String payerCurrentBalance) {}