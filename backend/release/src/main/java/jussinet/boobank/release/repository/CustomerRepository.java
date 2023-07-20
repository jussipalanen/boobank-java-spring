package jussinet.boobank.release.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jussinet.boobank.release.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findCustomerById(Integer id);
    public Customer findCustomerByEmail(String email);

    /**
     * Get the cumulative sum of all of the transactions
     * @param customer_id
     * @return
     */
    @Query(value = "SELECT SUM(t1.amount) OVER (ORDER BY t1.created_at) FROM transactions as t1 ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Float findCumulativeSum();


    /**
     * Get the monthly balance and with year
     * @param customer_id
     * @return
     */
    // @Query(value = "SELECT SUM(t1.amount) OVER (PARTITION BY date_trunc('MONTH', t1.created_at) ORDER BY created_at) " +
    //         "FROM transactions as t1 " +
    //         "WHERE EXTRACT('month' FROM t1.created_at) = :month " + 
    //         "AND EXTRACT('year' FROM t1.created_at) = :year " +
    //         "ORDER BY t1.created_at DESC LIMIT 1", nativeQuery = true)
    @Query(value = "SELECT sum FROM " + 
            "( " + 
            " SELECT t1.created_at as date, SUM(t1.amount) OVER (ORDER BY t1.created_at) FROM transactions as t1  " + 
            ") as trans " + 
            "WHERE EXTRACT(YEAR FROM date) = :year " + 
            "AND " + 
            "EXTRACT(MONTH FROM date) = :month " + 
            "ORDER BY date DESC LIMIT 1", nativeQuery = true)
    Float findMonthlyBalance(@Param(value = "month") Integer month, @Param(value = "year") Integer year);
}
