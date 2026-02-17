package br.com.simplified_elo_payment.user.domain.port;

import java.math.BigDecimal;

public interface IPortAccountProvider {
    boolean creationAccountEvent (Long userId, BigDecimal initialBalance);
}