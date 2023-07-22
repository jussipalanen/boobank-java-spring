package jussinet.boobank.release.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jussinet.boobank.release.entity.Transaction;
import jussinet.boobank.release.repository.queryinterfaces.TransactionApiData;
import jussinet.boobank.release.repository.queryinterfaces.TransactionData;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT id, created_at as date, amount FROM transactions", nativeQuery = true)
    List<TransactionApiData> findAllTransactions(Pageable pageable);

    /**
     * Fetching all of the transactions with the specific fields, and pagination
     * @return
     */
    @Query(value = "SELECT t.* " + 
            "FROM ( " + 
            "  SELECT  " + 
            " id, " + 
            " amount, " + 
            " DATE(created_at) AS date,  " + 
            " comment as message, " + 
            " customer_id, " + 
            " SUM(amount) OVER(ORDER BY created_at) AS cumulativesum  " + 
            "  FROM transactions " + 
            "  WHERE (:endDateStr IS NULL OR DATE(created_at) <= TO_TIMESTAMP(:endDateStr, 'YYYY-MM-DD')) " + 
            ") t  " + 
            "WHERE (:startDateStr IS NULL OR date >= TO_TIMESTAMP(:startDateStr, 'YYYY-MM-DD')) " + 
            "ORDER by date ASC", nativeQuery = true)
    Page<TransactionData> findAllPaged(@Param(value = "startDateStr") String startDateStr, @Param(value = "endDateStr") String endDateStr, Pageable page);
}
