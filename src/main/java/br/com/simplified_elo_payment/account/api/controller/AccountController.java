package br.com.simplified_elo_payment.account.api.controller;

import br.com.simplified_elo_payment.account.api.dto.creation.AccountCreationResquestDto;
import br.com.simplified_elo_payment.account.api.dto.transaction.TransactionRequestDto;
import br.com.simplified_elo_payment.account.application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/payment")
    public ResponseEntity<BigDecimal> paymentTransaction(@RequestBody TransactionRequestDto requestTransactionDto) {


        return ResponseEntity.ok().body(this.accountService
                .paymentTransaction(requestTransactionDto.amount(), requestTransactionDto.userId()).newBalance());
    }

    @PostMapping("/receipt")
    public ResponseEntity<BigDecimal> receiptTransaction(@RequestBody TransactionRequestDto requestTransactionDto) {
        return ResponseEntity.ok().body(this.accountService
                .receiptTransaction(requestTransactionDto.amount(), requestTransactionDto.userId()).newBalance());
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createNewAccount(@RequestBody AccountCreationResquestDto request) {
        return ResponseEntity.ok().body(this.accountService
                .createNewAccount(request.initialBalance(), request.userId(), request.paymentTypes()));
    }
}