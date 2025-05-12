package com.github.mrchcat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Balance {
    @NotNull
    private UUID client;
    @NotNull
    private Long account;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String currency;
}
