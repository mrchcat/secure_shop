package com.github.mrchcat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Payment {
    @NotNull
    private UUID paymentId;
    @NotNull
    private UUID payer;
    @NotNull
    private UUID recipient;
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
}
