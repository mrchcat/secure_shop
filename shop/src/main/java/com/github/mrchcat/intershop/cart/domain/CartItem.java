package com.github.mrchcat.intershop.cart.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "cart_item")
@Builder
public class CartItem {
    @Id
    private long id;

    @Column("cart_id")
    @NotNull
    private long cartId;

    @Column("item_id")
    @NotNull
    private long itemId;

    @Column("quantity")
    private long quantity;

}
