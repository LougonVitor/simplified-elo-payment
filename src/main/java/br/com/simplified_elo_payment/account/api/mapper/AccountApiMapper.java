package br.com.simplified_elo_payment.account.api.mapper;

import br.com.simplified_elo_payment.account.api.dto.transaction.TransactionRequestDto;
import br.com.simplified_elo_payment.account.application.dto.PerformTransactionCommand;

public class AccountApiMapper {
    public static PerformTransactionCommand toAccountServiceRequest(TransactionRequestDto transactionRequestDto) {
        return new PerformTransactionCommand(
                transactionRequestDto.amount()
                , transactionRequestDto.receivingUserId()
                , transactionRequestDto.payingUserId()
                , transactionRequestDto.paymentType()
        );
    }
}