package com.demo.bank.transactionMs.controller;

import com.demo.bank.transactionMs.dto.TransferRequest;
import com.demo.bank.transactionMs.model.Transaction;
import com.demo.bank.transactionMs.repository.TransactionRepository;
import com.demo.bank.transactionMs.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService service){
        this.transactionService = service;
    }

    @PostMapping("/registerDeposit/{id}")
    public Mono<Transaction> registerDeposit(@PathVariable String id, @RequestBody Double amount){
        return transactionService.registerDeposit(id,amount);
    }

    @PostMapping("/registerWithdrawal/{accountId}")
    public Mono<Transaction> registerWithdrawal(@PathVariable String accountId, @RequestBody Double amount) {
        return transactionService.registerWithdrawal(amount, accountId);
    }

    @PostMapping("/transfer")
        public Mono<Transaction> registerTransfer1(@RequestBody TransferRequest transferRequest) {
        return transactionService.registerTransfer(transferRequest.getAccountId(), transferRequest.getAccountDestination(), transferRequest.getAmount());
    }

    @GetMapping("/getHistory/{accountId}")
      public Flux<Transaction> getHistory(@PathVariable String accountId){
        return transactionService.getTransactionHistory(accountId);
    }



}
