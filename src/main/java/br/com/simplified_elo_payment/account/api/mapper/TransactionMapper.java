package br.com.simplified_elo_payment.account.api.mapper;

import br.com.simplified_elo_payment.account.api.dto.transaction.TransactionRequestDto;
import br.com.simplified_elo_payment.account.application.dto.transaction.PerformTransactionCommand;

public class TransactionMapper {
    public static PerformTransactionCommand toAccountServiceRequest(TransactionRequestDto requestDto) {
        return new PerformTransactionCommand(
                requestDto.amount()
                , requestDto.receivingUserId()
                , requestDto.payingUserId()
                , requestDto.paymentType()
        );
    }
}