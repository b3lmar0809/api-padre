package com.barrioapp.api_padre.controller;

import com.barrioapp.api_padre.dto.BalanceResponce;
import com.barrioapp.api_padre.dto.FinanceRequest;
import com.barrioapp.api_padre.dto.FinanceResponse;
import com.barrioapp.api_padre.service.FinanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FinanceController class
 *
 * @Version: 1.0.0 - 15 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 15 abr. 2026
 */
@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    @PostMapping
    public ResponseEntity<FinanceResponse> registerMovement(@Valid @RequestBody FinanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeService.registerMovement(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FinanceResponse>> listMovements(@PathVariable Long userId) {
        return ResponseEntity.ok(financeService.listMovement(userId));
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<BalanceResponce> getBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(financeService.calculateBalance(userId));
    }
}
