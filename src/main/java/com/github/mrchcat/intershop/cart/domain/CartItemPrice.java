package com.github.mrchcat.intershop.cart.domain;

import com.github.mrchcat.intershop.enums.Unit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;


@Getter
@Setter
public class CartItemPrice {
    @Id
    private long id;

    @Column("cart_id")
    private long cartId;

    @Column("item_id")
    private long itemId;

    @Column("name")
    private String title;

    @Column("description")
    private String description;

    @Column("picture_path")
    private String imgPath;

    @Column("unit")
    private Unit unit;

    @Column("quantity")
    private long quantity;

    @Column("price")
    private BigDecimal price;
}

