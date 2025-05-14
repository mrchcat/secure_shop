package com.github.mrchcat.intershop.item.domain;


import com.github.mrchcat.intershop.enums.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "items")
public class Item implements Serializable {
    @Id
    private long id;

    @Column("article_number")
    private UUID articleNumber;

    @Column("name")
    @NotNull
    @NotBlank
    @Length(max = 256)
    private String title;

    @Column("description")
    @NotNull(message = "описание товара не может отсутствовать")
    @NotBlank(message = "описание товара не может отсутствовать")
    private String description;

    @Column("picture_path")
    @Length(max = 1000)
    private String imgPath;

    @Column("price")
    @NotNull(message = "товар должен иметь цену")
    @PositiveOrZero(message = "цена должна быть положительным числом")
    private BigDecimal price;

    @Column("unit")
    @NotNull(message = "единица измерения не может отсутствовать")
    private Unit unit;
}
