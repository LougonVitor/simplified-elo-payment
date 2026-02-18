package br.com.simplified_elo_payment.account.api.dto.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionRequestDto(
        @NotNull(message = "Amount cannot be null!")
        String amount

        , @NotNull(message = "ReceivingUserId cannot be null!")
        Long receivingUserId

        , @NotNull(message = "PayingUserId cannot be null!")
        Long payingUserId

        , @NotNull(message = "PaymentType cannot be null!")
        String paymentType
) {}