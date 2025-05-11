package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class Payment {
    @NotNull
    private UUID paymentId;
    @NotNull
    private UUID payer;
    @NotNull
    private UUID recipient;
    @Positive
    @NotNull
    private BigDecimal amount;
}
