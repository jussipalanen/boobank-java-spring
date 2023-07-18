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
    @Query(value = "select t.id as id, t.amount as amount, t.created_at as date from transactions t ORDER BY t.created_at DESC", nativeQuery = true)
    List<TransactionData> findAllTransactions();

    @Query(value = "SELECT id, amount, created_at, long_message, message, name, comment, customer_id FROM public.transactions WHERE EXTRACT('month' FROM created_at) = :month AND EXTRACT('year' FROM created_at) = :year ORDER BY created_at DESC", nativeQuery = true)
    List<Transaction> findAllTransactionsByMonthAndYear(@Param(value = "month") Integer month, @Param(value = "year") Integer year);
}
