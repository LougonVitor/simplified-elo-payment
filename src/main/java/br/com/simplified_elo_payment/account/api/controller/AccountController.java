package br.com.simplified_elo_payment.account.api.controller;

import br.com.simplified_elo_payment.account.api.dto.creation.CreationResquestDto;
import br.com.simplified_elo_payment.account.api.dto.transaction.TransactionRequestDto;
import br.com.simplified_elo_payment.account.api.mapper.CreationMapper;
import br.com.simplified_elo_payment.account.api.mapper.TransactionMapper;
import br.com.simplified_elo_payment.account.application.dto.creation.PerformCreationCommand;
import br.com.simplified_elo_payment.account.application.dto.transaction.PerformTransactionCommand;
import br.com.simplified_elo_payment.account.application.dto.transaction.TransactionResult;
import br.com.simplified_elo_payment.account.application.service.CreationService;
import br.com.simplified_elo_payment.account.application.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private TransactionService accountService;
    @Autowired
    private CreationService accountCreationService;

    @PostMapping("/payment")
    public ResponseEntity<TransactionResult> transaction(@RequestBody @Valid TransactionRequestDto requestDto) {
        PerformTransactionCommand mappedRequest = TransactionMapper.toAccountServiceRequest(requestDto);

        TransactionResult response = this.accountService.executeTransaction(mappedRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createNewAccount(@RequestBody CreationResquestDto requestDto) {
        PerformCreationCommand mappedRequest = CreationMapper.toCreationCommand(requestDto);

        Long idCreated = this.accountCreationService.createAccount(mappedRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(idCreated);
    }
}