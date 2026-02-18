package br.com.simplified_elo_payment.user.infrastructure.adapter;

import br.com.simplified_elo_payment.user.domain.port.IPortAccountProvider;
import br.com.simplified_elo_payment.user.infrastructure.adapter.dto.AccountRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class AccountServiceClient implements IPortAccountProvider {
    private final RestClient restClient;

    public AccountServiceClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8080/account").build();
    }

    @Override
    public boolean creationAccountEvent(Long userId, BigDecimal initialBalance, Set<String> paymentTypes) {
        try{
            var responseExternalCalling = restClient.post()
                    .uri("/create")
                    .body(new AccountRequest(userId, initialBalance, paymentTypes))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new RuntimeException("Erro na API de Account: " + response.getStatusCode());
                    })
                    .toBodilessEntity();

            return responseExternalCalling.getStatusCode().is2xxSuccessful();
        } catch (Exception ex) {
            return false;
        }
    }
}