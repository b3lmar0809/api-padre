package com.barrioapp.api_padre.service.serviceImpl;

import com.barrioapp.api_padre.dto.BalanceResponce;
import com.barrioapp.api_padre.dto.FinanceRequest;
import com.barrioapp.api_padre.dto.FinanceResponse;
import com.barrioapp.api_padre.model.Finance;
import com.barrioapp.api_padre.model.MovementType;
import com.barrioapp.api_padre.model.User;
import com.barrioapp.api_padre.repository.FinanceRepository;
import com.barrioapp.api_padre.repository.UserRepository;
import com.barrioapp.api_padre.service.FinanceService;
import com.barrioapp.api_padre.util.DateUtils;
import com.barrioapp.api_padre.util.EnumUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FinanceServiceImpl class
 *
 * @Version: 1.0.1 - 19 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 14 abr. 2026
 */
@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

    private final FinanceRepository financeRepository;
    private final UserRepository userRepository;

    @Override
    public FinanceResponse registerMovement(FinanceRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MovementType type = EnumUtils.parse(MovementType.class, request.getType(), "Invalid movement type. Use INCOME or EXPENSE");

        Finance finance = new Finance();
        finance.setUser(user);
        finance.setType(type);
        finance.setAmount(request.getAmount());
        finance.setDescription(request.getDescription());

        Finance saved = financeRepository.save(finance);

        return new FinanceResponse(
                saved.getId(),
                saved.getType().name(),
                saved.getAmount(),
                saved.getDescription(),
                saved.getDate()
        );
    }

    @Override
    public List<FinanceResponse> listMovement(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return financeRepository.findByUserId(userId).stream()
                .map(f -> new FinanceResponse(
                        f.getId(),
                        f.getType().name(),
                        f.getAmount(),
                        f.getDescription(),
                        f.getDate()))
                .toList();
    }

    @Override
    public BalanceResponce calculateBalance(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Finance> movements = financeRepository.findByUserId(userId);

        double totalIncome = movements.stream()
                .filter(f -> f.getType() == MovementType.INCOME)
                .mapToDouble(Finance::getAmount)
                .sum();

        double totalExpenses = movements.stream()
                .filter(f -> f.getType() == MovementType.EXPENSE)
                .mapToDouble(Finance::getAmount)
                .sum();

        String period = DateUtils.currentMonthPeriod();

        return new BalanceResponce(totalIncome, totalExpenses, totalIncome - totalExpenses, period);
    }
}
