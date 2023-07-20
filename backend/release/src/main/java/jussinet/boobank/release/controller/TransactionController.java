package jussinet.boobank.release.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jussinet.boobank.release.repository.TransactionData;
import jussinet.boobank.release.repository.TransactionRepository;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    TransactionController(TransactionRepository transactionRepository) {
        this.repository = transactionRepository;
    }

    /**
     * Get all of the transactions
     * The request parameters are optional: month, year, and customer_id
     * 
     * @return
     */
    @GetMapping(value = "/transactions")
    List<TransactionData> all(@RequestBody(required = false) Map<String, String> json) {

        final Integer month = json != null && json.get("month") != null ? Integer.parseInt(json.get("month")) : null;
        final Integer year = json != null && json.get("year") != null ? Integer.parseInt(json.get("year")) : null;
        final Integer customer_id = json != null && json.get("customer_id") != null ? Integer.parseInt(json.get("customer_id")) : null;
        return repository.findAllTransactions(month, year, customer_id);
    }

    /**
     * Check total count of the transactions
     * @param json
     * @return
     */
    @GetMapping(value = "/transactions/count")
    Integer count(@RequestBody(required = false) Map<String, String> json) {

        final Integer month = json != null && json.get("month") != null ? Integer.parseInt(json.get("month")) : null;
        final Integer year = json != null && json.get("year") != null ? Integer.parseInt(json.get("year")) : null;
        final Integer customer_id = json != null && json.get("customer_id") != null ? Integer.parseInt(json.get("customer_id")) : null;
        return repository.findAllTransactions(month, year, customer_id).size();
    }

}
