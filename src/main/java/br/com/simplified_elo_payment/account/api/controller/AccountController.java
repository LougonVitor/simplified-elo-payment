package br.com.simplified_elo_payment.account.api.controller;

import br.com.simplified_elo_payment.account.api.dto.AccountResponseDto;
import br.com.simplified_elo_payment.account.api.dto.AccountResquestDto;
import br.com.simplified_elo_payment.account.application.dto.AccountServiceResponseDto;
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
    public ResponseEntity<BigDecimal> paymentTransaction(@RequestBody AccountResquestDto requestDto) {
        return ResponseEntity.ok().body(this.accountService.paymentTransaction(requestDto.amount(), requestDto.UserId()).newBalance());
    }

    @PostMapping("/receipt")
    public ResponseEntity<BigDecimal> receiptTransaction(@RequestBody AccountResquestDto requestDto) {
        return ResponseEntity.ok().body(this.accountService.receiptTransaction(requestDto.amount(), requestDto.UserId()).newBalance());
    }
}