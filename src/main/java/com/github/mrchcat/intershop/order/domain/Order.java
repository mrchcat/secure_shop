package com.github.mrchcat.intershop.order.domain;

import com.github.mrchcat.intershop.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

//@NamedEntityGraph(name = "graph.order", attributeNodes = @NamedAttributeNode("orderItems"))
@NamedEntityGraph(name = "graph.order.items",
        attributeNodes = @NamedAttributeNode(value = "orderItems", subgraph = "items"),
        subgraphs = @NamedSubgraph(name = "items", attributeNodes = @NamedAttributeNode("item")))


public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number",length = 256)
//    @NotNull(message = "поле номера не может быть пустым")
    @NotBlank (message = "поле номера не может быть пустым")
    @Length(max = 256, message = "поле номера не может быть больше 256 знаков")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "заказ должен иметь пользователя")
    User user;

    @Column(name = "created")
//    @CreationTimestamp
    private LocalDateTime created;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(name = "total_sum")
    @PositiveOrZero(message = "сумма заказа не может быть отрицательным числом")
    private BigDecimal totalSum;

}
