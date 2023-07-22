package jussinet.boobank.release.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jussinet.boobank.release.entity.Transaction;
import jussinet.boobank.release.repository.TransactionRepository;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionService( TransactionRepository transactionRepository )
    {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactions()
    {
        return transactionRepository.findAll();
    }


    public Transaction saveTransaction( Transaction transaction )
    {
        return transactionRepository.save(transaction);
    }
}
