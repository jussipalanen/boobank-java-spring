package jussinet.boobank.release.repository;

import java.util.List;
import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jussinet.boobank.release.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    /**
     * Fetching all of the transactions with the specific fields
     * @return
     */
    @Query(value = "select t.id as id, t.amount as amount, t.created_at as date from transactions t ORDER BY t.created_at DESC", nativeQuery = true)
    List<TransactionData> findAllTransactions();
}
