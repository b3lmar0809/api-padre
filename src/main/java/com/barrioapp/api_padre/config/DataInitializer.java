package com.barrioapp.api_padre.config;

import com.barrioapp.api_padre.model.Plan;
import com.barrioapp.api_padre.model.PlanType;
import com.barrioapp.api_padre.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DataInitializer class
 *
 * @Version: 1.0.0 - 11 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 11 abr. 2026
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PlanRepository planRepository;

    @Override
    public void run(String... args) {
        if (planRepository.count() == 0) {
            planRepository.saveAll(List.of(
                    new Plan(null, PlanType.FREE,   20,    0.0),
                    new Plan(null, PlanType.BASIC, 100, 4990.0),
                    new Plan(null, PlanType.PRO,   999, 9990.0)
            ));
        }
    }
}
