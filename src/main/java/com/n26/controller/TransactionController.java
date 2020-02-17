package com.n26.controller;

import com.n26.exception.TransactionLimitException;
import com.n26.exception.TransactionFutureException;
import com.n26.model.Transaction;
import com.n26.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Create a transaction
     *
     * @param transaction
     * @returnResponseEntity<Transaction>
     */
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction) {
        try {
            transactionService.create(transaction);
        } catch (TransactionLimitException e) {
            return new ResponseEntity<>(NO_CONTENT);
        } catch (TransactionFutureException e) {
            return new ResponseEntity<>(UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(CREATED);
    }

    /**
     * Remove all transactions
     *
     * @return ResponseEntity<Void>
     */
    @DeleteMapping(value = "/transactions")
    public ResponseEntity<Void> deleteAll() {
        transactionService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
