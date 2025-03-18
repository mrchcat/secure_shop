package com.github.mrchcat.intershop;

import com.github.mrchcat.intershop.enums.Unit;
import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.product.domain.Item;
import com.github.mrchcat.intershop.user.domain.User;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Component
public class Tester {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public void run() {
        long milkId;
        EntityManager em = entityManagerFactory.createEntityManager();
        Item milk = Item.builder()
                .name("молоко")
                .description("Молоко жирностью 3,5%")
                .picture(new byte[]{123})
                .basePrice(BigDecimal.valueOf(1434, 2))
                .quantityOnStock(10)
                .unit(Unit.PIECE)
                .build();

        Item vodka = Item.builder()
                .name("водка")
                .description("Водка крепостью 40 градусов")
                .picture(new byte[]{123})
                .basePrice(BigDecimal.valueOf(56434, 2))
                .quantityOnStock(200)
                .unit(Unit.PIECE)
                .build();

        User anna = User.builder()
                .name("Anna")
                .email("qwe@qweq.ru")
                .build();

        Order firstOrder = new Order();
        firstOrder.setNumber("123124r1");
        firstOrder.setUser(anna);

        long milkOrderQuantity = 1;
        milk.setQuantityOnStock(milk.getQuantityOnStock() - milkOrderQuantity);

        OrderItem milkOrder = OrderItem.builder()
                .order(firstOrder)
                .item(milk)
                .orderPrice(milk.getBasePrice())
                .quantity(milkOrderQuantity)
                .unit(Unit.PIECE)
                .sum(milk.getBasePrice().multiply(BigDecimal.valueOf(milkOrderQuantity)))
                .build();

        long vodkaOrderQuantity = 10;
        vodka.setQuantityOnStock(vodka.getQuantityOnStock() - vodkaOrderQuantity);
        OrderItem vodkaOrder = OrderItem.builder()
                .order(firstOrder)
                .item(vodka)
                .orderPrice(vodka.getBasePrice())
                .quantity(vodkaOrderQuantity)
                .unit(Unit.PIECE)
                .sum(vodka.getBasePrice().multiply(BigDecimal.valueOf(vodkaOrderQuantity)))
                .build();

        Set<OrderItem> orderItems = Set.of(milkOrder, vodkaOrder);

        BigDecimal totalSum = BigDecimal.ZERO;
        for (OrderItem oi : orderItems) {
            totalSum = totalSum.add(oi.getSum());
        }
        firstOrder.setTotalSum(totalSum);

        em.getTransaction().begin();

        em.persist(anna);
        em.persist(milk);
        em.persist(vodka);
        em.persist(firstOrder);
        em.persist(vodkaOrder);
        em.persist(milkOrder);

        milkId = milk.getId();
        long userId=anna.getId();

        em.getTransaction().commit();
        em.close();

        //****************
        em = entityManagerFactory.createEntityManager();
        EntityGraph graph = em.getEntityGraph("graph.order.items");
        Map hints = Map.of("jakarta.persistence.fetchgraph", graph);

        Order myorder = em.find(Order.class, firstOrder.getId(), hints);
//        System.out.println(myorder);

        Item item1 = em.find(Item.class, milkId);
        Item item2 = em.find(Item.class, milkId);
        System.out.println(item1 == item2);
        System.out.println(item1);

        anna=em.find(User.class,userId);
        Order annaBasket=new Order();
        anna.setBasket(annaBasket);
        em.persist(anna);
        em.persist(annaBasket);
        entityManagerFactory.close();
    }
}
