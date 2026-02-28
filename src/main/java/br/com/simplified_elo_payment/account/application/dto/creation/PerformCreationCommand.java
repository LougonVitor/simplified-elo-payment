package br.com.simplified_elo_payment.account.application.dto.creation;

import br.com.simplified_elo_payment.account.domain.valueobjects.PaymentType;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record PerformCreationCommand (BigDecimal initialBalance, Long userId, Set<String> paymentTypes) {
    public Set<PaymentType> getPaymentTypesAsEnum() {
        return paymentTypes.stream().map(
                types -> PaymentType.valueOf(types.toUpperCase()))
                .collect(Collectors.toSet());
    }
}