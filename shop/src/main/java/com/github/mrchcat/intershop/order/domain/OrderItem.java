package com.github.mrchcat.intershop.order.domain;

import com.github.mrchcat.intershop.enums.Unit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "order_item")
public class OrderItem {
    @Id
    private long id;

    @Column("order_id")
    @NotNull
    private long orderId;

    @Column("item_id")
    @NotNull
    private long itemId;

    @Column("quantity")
    @NotNull
    @Positive
    private long quantity;

    @Column("unit")
    @NotNull
    private Unit unit;

    @Column("price")
    @PositiveOrZero
    private BigDecimal orderPrice;

    @Column("sum")
    private BigDecimal sum;

}
