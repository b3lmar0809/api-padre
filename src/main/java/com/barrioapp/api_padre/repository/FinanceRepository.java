package com.barrioapp.api_padre.repository;

import com.barrioapp.api_padre.model.Finance;
import com.barrioapp.api_padre.model.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * FinanzasRepository class
 *
 * @Version: 1.0.0 - 14 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 2026/04/14
 */
@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {

    List<Finance> findByUserId(Long userId);

    List<Finance> findByUserIdAndType(Long userId, MovementType type);

    List<Finance> findByUserIdAndDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
