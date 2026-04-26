package com.barrioapp.api_padre.repository;

import com.barrioapp.api_padre.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * TransactionRepository class
 *
 * @Version: 1.0.0 - 20 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 20 abr. 2026
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByCommerceOrder(String commerceOrder);

    Boolean existsByFlowOrder(String flowOrder);

    List<Transaction> findByUserId(Long userId);
}
