package br.com.simplified_elo_payment.account.api.controller;

import br.com.simplified_elo_payment.account.api.dto.creation.AccountCreationResquestDto;
import br.com.simplified_elo_payment.account.api.dto.transaction.TransactionRequestDto;
import br.com.simplified_elo_payment.account.application.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/payment")
    public ResponseEntity<String> transaction(@RequestBody @Valid TransactionRequestDto transactionRequestDto) {
        String response = this.accountService.transaction(
                transactionRequestDto.amount()
                , transactionRequestDto.receivingUserId()
                , transactionRequestDto.payingUserId()
        ).response();

        return ResponseEntity.ok().body("Transaction successful\n" + response);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createNewAccount(@RequestBody AccountCreationResquestDto request) {
        return ResponseEntity.ok().body(this.accountService
                .createNewAccount(request.initialBalance(), request.userId(), request.paymentTypes()));
    }
}