package com.github.mrchcat.intershop.user.domain;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "users")
public class User {
    @Id
    private long id;

    @Column("email")
    @NotNull
    @Length(max = 256)
    @Email
    private String email;

    @Column("name")
    @NotNull
    @NotBlank
    @Length(max = 256)
    private String name;

    @Column("payment_id")
    @NotNull
    private UUID paymentId;
}
