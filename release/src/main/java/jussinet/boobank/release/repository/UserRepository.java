package jussinet.boobank.release.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jussinet.boobank.release.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
