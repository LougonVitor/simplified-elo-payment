package br.com.simplified_elo_payment.account.api.mapper;

import br.com.simplified_elo_payment.account.api.dto.creation.CreationResquestDto;
import br.com.simplified_elo_payment.account.application.dto.creation.PerformCreationCommand;

public class CreationMapper {
    public static PerformCreationCommand toCreationCommand(CreationResquestDto requestDto){
        return new PerformCreationCommand(
                requestDto.initialBalance()
                , requestDto.userId()
                , requestDto.paymentTypes()
        );
    }
}