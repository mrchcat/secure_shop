package com.github.mrchcat.intershop.cart.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor

@Table("carts")
public class Cart {
    @Id
    private long id;

    @Column("user_id")
    private long userId;

}
