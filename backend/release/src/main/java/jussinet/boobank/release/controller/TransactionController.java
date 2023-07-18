package jussinet.boobank.release.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jussinet.boobank.release.repository.TransactionData;
import jussinet.boobank.release.repository.TransactionRepository;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    TransactionController(TransactionRepository transactionRepository) {
        this.repository = transactionRepository;
    }

    /**
     * Get all of the transactions
     * 
     * @return
     */
    @GetMapping(value = "/api/transactions")
    List<TransactionData> all() {
        return repository.findAllTransactions();
    }
}
