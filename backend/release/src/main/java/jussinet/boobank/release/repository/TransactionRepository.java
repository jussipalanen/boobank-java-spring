package jussinet.boobank.release.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jussinet.boobank.release.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    /**
     * Fetching all of the transactions with the specific fields
     * @return
     */
    @Query(value = "SELECT transactions.id, transactions.created_at as date, transactions.amount, transactions.comment as message FROM transactions " +
    "WHERE (:month is null OR EXTRACT('month' FROM created_at) = :month) AND " + 
    "(:year is null OR EXTRACT('year' FROM created_at) = :year) AND " +
    "(:customer_id is null OR customer_id = :customer_id) " + 
    "ORDER BY created_at DESC", nativeQuery = true)
    List<TransactionData> findAllTransactions(@Param(value = "month") Integer month, @Param(value = "year") Integer year, @Param(value = "customer_id") Integer customer_id);
}
