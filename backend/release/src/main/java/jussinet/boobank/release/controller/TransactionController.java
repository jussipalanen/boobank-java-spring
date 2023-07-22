package jussinet.boobank.release.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jussinet.boobank.release.repository.TransactionRepository;
import jussinet.boobank.release.repository.queryinterfaces.TransactionApiData;

@RestController
@RequestMapping("/api/v1")
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
    List<TransactionApiData> all(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "date") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Direction sortDirection) {

        if (sortDirection.equals(Sort.Direction.DESC)) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }

        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of((page != null && page > 0 ? (page - 1) : 0),
                (size != null && size > 0 ? size : Integer.MAX_VALUE), sort);
        return repository.findAllTransactions(pageable);
    }

    /**
     * Check total count of the transactions
     * 
     * @param json
     * @return
     */
    @GetMapping(value = "/transactions/count")
    Integer count() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return repository.findAllTransactions(pageable).size();
    }

}
