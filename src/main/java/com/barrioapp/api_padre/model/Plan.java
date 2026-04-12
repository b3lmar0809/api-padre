package com.barrioapp.api_padre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Plan class
 *
 * @Version: 1.0.0 - 11 abr. 2026
 * @Author: Matias Belmar - mati.belmar0625@gmail.com
 * @Since: 1.0.0 - 11 abr. 2026
 */
@Entity
@Table(name = "plan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PlanType type;

    @Column(nullable = false)
    private Integer limitProduct;

    @Column(nullable = false)
    private Double price;
}
