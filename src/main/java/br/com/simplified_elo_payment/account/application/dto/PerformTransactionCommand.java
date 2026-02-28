package br.com.simplified_elo_payment.account.application.dto;

public record PerformTransactionCommand(String amount, Long receivingUserID, Long payingUserId, String paymentType) {}