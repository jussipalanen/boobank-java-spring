package jussinet.boobank.release.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import jussinet.boobank.release.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
