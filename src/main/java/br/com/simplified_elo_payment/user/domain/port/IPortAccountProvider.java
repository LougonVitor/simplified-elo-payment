package br.com.simplified_elo_payment.user.domain.port;

import java.math.BigDecimal;
import java.util.Set;

public interface IPortAccountProvider {
    boolean creationAccountEvent (Long userId, BigDecimal initialBalance, Set<String> paymentTypes);
}