package com.barrioapp.api_padre.service;

import com.barrioapp.api_padre.dto.BalanceResponce;
import com.barrioapp.api_padre.dto.FinanceRequest;
import com.barrioapp.api_padre.dto.FinanceResponse;

import java.util.List;

/**
 * FinanceService class
 *
 * @Version: 1.0.0 - 14 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 2026/04/14
 */

public interface FinanceService {

    FinanceResponse registerMovement(FinanceRequest request);
    List<FinanceResponse> listMovement(Long userId);
    BalanceResponce calculateBalance(Long userId);
}
