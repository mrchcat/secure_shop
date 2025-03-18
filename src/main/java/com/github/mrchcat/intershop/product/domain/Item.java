package com.github.mrchcat.intershop.product.domain;

import com.github.mrchcat.intershop.enums.Unit;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 256)
    @NotNull
    @NotBlank
    @Length(max = 256)
    private String name;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Column(name = "picture", nullable = false)
    @NotNull
    private byte[] picture;

    @Column(name = "price", nullable = false)
    @NotNull
    @PositiveOrZero
    private BigDecimal basePrice;

    @Column(name = "stock_quantity", nullable = false)
    @NotNull
    @PositiveOrZero
    private long quantityOnStock;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Unit unit;
}
