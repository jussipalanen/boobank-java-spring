package jussinet.boobank.release.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jussinet.boobank.release.entity.Customer;
import jussinet.boobank.release.entity.Transaction;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findCustomerById(Integer id);
    public Customer findCustomerByEmail(String email);

    /**
     * Get the cumulative sum of all of the transactions
     * @param customer_id
     * @return
     */
    @Query(value = "SELECT SUM(t1.amount) OVER (ORDER BY t1.created_at) FROM transactions as t1 ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Float findCumulativeBalance();


    /**
     * Get the cumulative balances from all of the transactions
     * @return
     */
    @Query(value = "SELECT t1.id, t1.amount, t1.created_at, t1.comment, (SUM(t1.amount) OVER (ORDER BY t1.created_at)) as cumulativebalance FROM transactions as t1 ORDER BY created_at ASC", nativeQuery = true)
    List<Transaction> findCumulativeBalances();

    /**
     * Get the monthly balance and with year
     * @param customer_id
     * @return
     */
    @Query(value = "SELECT sum FROM " + 
            "( " + 
            " SELECT t1.created_at as date, SUM(t1.amount) OVER (ORDER BY t1.created_at) FROM transactions as t1  " + 
            ") as trans " + 
            "WHERE (TO_TIMESTAMP(:startDateStr, 'YYYY-MM-DD') is null OR date >= TO_TIMESTAMP(:startDateStr, 'YYYY-MM-DD') AND TO_TIMESTAMP(:endDateStr, 'YYYY-MM-DD') is null OR date <= TO_TIMESTAMP(:endDateStr, 'YYYY-MM-DD')) " + 
            "ORDER BY date DESC LIMIT 1", nativeQuery = true)
    Float findMonthlyBalance(@Param(value = "startDateStr") String startDateStr, @Param(value = "endDateStr") String endDateStr);
}
