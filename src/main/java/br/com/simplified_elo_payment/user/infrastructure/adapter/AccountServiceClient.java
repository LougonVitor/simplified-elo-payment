package br.com.simplified_elo_payment.user.infrastructure.adapter;

import br.com.simplified_elo_payment.user.domain.port.IPortAccountProvider;
import br.com.simplified_elo_payment.user.infrastructure.adapter.dto.AccountRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class AccountServiceClient implements IPortAccountProvider {
    private final RestClient restClient;

    public AccountServiceClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("localhost:8080/account").build();
    }

    @Override
    public boolean creationAccountEvent(Long userId, BigDecimal initialBalance) {
        try{
            var responseExternalCalling = restClient.post()
                    .uri("/account/create")
                    .body(new AccountRequest(initialBalance, userId))
                    .retrieve()
                    .toBodilessEntity();

            return responseExternalCalling.getStatusCode().is2xxSuccessful();
        } catch (Exception ex) {
            return false;
        }
    }
}