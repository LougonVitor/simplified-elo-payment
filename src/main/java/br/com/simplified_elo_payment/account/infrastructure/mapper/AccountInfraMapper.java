package br.com.simplified_elo_payment.account.infrastructure.mapper;

import br.com.simplified_elo_payment.account.domain.entity.AccountEntity;
import br.com.simplified_elo_payment.account.infrastructure.entity.AccountJpaEntity;

public class AccountInfraMapper {
    public static AccountEntity toDomainEntity(AccountJpaEntity jpaEntity) {
        return new AccountEntity(
                jpaEntity.getId()
                , jpaEntity.getUserId()
                , jpaEntity.getBalance()
                , jpaEntity.getPaymentTypesAccepted()
        );
    }

    public static AccountJpaEntity toJpaEntity(AccountEntity entity) {
        return new AccountJpaEntity(
                entity.getId()
                , entity.getUserId()
                , entity.getBalance()
                , entity.getPaymentType()
        );
    }
}