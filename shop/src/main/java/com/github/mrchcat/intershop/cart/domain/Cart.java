package com.github.mrchcat.intershop.cart.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Table("carts")
public class Cart {
    @Id
    private long id;

    @Column("user_id")
    private long userId;

    public Cart(long userId) {
        this.userId = userId;
    }
}
