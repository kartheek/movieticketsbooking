package com.app.movieticket.repositories;

import com.app.movieticket.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByPhone(String phone);

    Optional<Customer> findCustomerByEmail(String email);
}
