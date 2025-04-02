package com.github.mrchcat.intershop.order.domain;


import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "orders")

public class Order {
    @Id
    private long id;

    @Column("number")
    @Length(max = 256, message = "поле номера не может быть больше 256 знаков")
    private String number;

    @Column("user_id")
    private long userId;

    @Column("created")
    private LocalDateTime created = LocalDateTime.now();

    @Column("total_sum")
    @PositiveOrZero(message = "сумма заказа не может быть отрицательным числом")
    private BigDecimal totalSum;

}
