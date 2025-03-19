package com.github.mrchcat.intershop.order.domain;

import com.github.mrchcat.intershop.enums.Unit;
import com.github.mrchcat.intershop.item.domain.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
//    @ToString.Exclude
    private Item item;

    @Column(name = "quantity", nullable = false)
    @NotNull
    @Positive
    private long quantity;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Unit unit;

    @Column(name = "price", nullable = false)
    @NotNull
    @PositiveOrZero
    private BigDecimal orderPrice;

    @Column(name = "sum", nullable = false)
    @NotNull
    private BigDecimal sum;

}
