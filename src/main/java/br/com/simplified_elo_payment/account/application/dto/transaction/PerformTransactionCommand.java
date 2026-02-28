package br.com.simplified_elo_payment.account.application.dto.transaction;

import java.math.BigDecimal;

public record PerformTransactionCommand(String amount, Long receivingUserID, Long payingUserId, String paymentType) {
    public BigDecimal getAmountAsBigDecimal() {
        return (amount != null) ? new BigDecimal(amount) : BigDecimal.ZERO;
    }
}