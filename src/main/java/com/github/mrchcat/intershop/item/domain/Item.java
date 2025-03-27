package com.github.mrchcat.intershop.item.domain;

import com.github.mrchcat.intershop.enums.Unit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import java.util.Objects;
import java.util.UUID;

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

    @Column(name = "article_number")
    private UUID articleNumber;

    @Column(name = "name", nullable = false, length = 256)
    @NotNull
    @NotBlank
    @Length(max = 256)
    private String title;

    @Column(name = "description", nullable = false)
    @NotNull(message = "описание товара не может отсутствовать")
    @NotBlank(message = "описание товара не может отсутствовать")
    private String description;

    @Column(name = "picture_path")
    @Length(max = 1000)
    private String imgPath;

    @Column(name = "price", nullable = false)
    @NotNull (message = "товар должен иметь цену")
    @PositiveOrZero (message = "цена должна быть положительным числом")
    private BigDecimal price;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "единица измерения не может отсутствовать")
    private Unit unit;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item item)) return false;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
