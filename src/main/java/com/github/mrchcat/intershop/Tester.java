package com.github.mrchcat.intershop;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Tester {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public void run() {
//        long milkId;
//        EntityManager em = entityManagerFactory.createEntityManager();
//        Item milk = Item.builder()
//                .title("молоко")
//                .description("Молоко жирностью 3,5%")
////                .picture(new byte[]{123})
//                .price(BigDecimal.valueOf(1434, 2))
//                .count(10)
//                .unit(Unit.PIECE)
//                .build();
//
//        Item vodka = Item.builder()
//                .title("водка")
//                .description("Водка крепостью 40 градусов")
////                .picture(new byte[]{123})
//                .price(BigDecimal.valueOf(56434, 2))
//                .count(200)
//                .unit(Unit.PIECE)
//                .build();
//
//        User anna = User.builder()
//                .name("Anna")
//                .email("qwe@qweq.ru")
//                .build();
//
//        Order firstOrder = new Order();
//        firstOrder.setNumber("123124r1");
//        firstOrder.setUser(anna);
//
//        long milkOrderQuantity = 1;
//        milk.setCount(milk.getCount() - milkOrderQuantity);
//
//        OrderItem milkOrder = OrderItem.builder()
//                .order(firstOrder)
//                .item(milk)
//                .orderPrice(milk.getPrice())
//                .quantity(milkOrderQuantity)
//                .unit(Unit.PIECE)
//                .sum(milk.getPrice().multiply(BigDecimal.valueOf(milkOrderQuantity)))
//                .build();
//
//        long vodkaOrderQuantity = 10;
//        vodka.setCount(vodka.getCount() - vodkaOrderQuantity);
//        OrderItem vodkaOrder = OrderItem.builder()
//                .order(firstOrder)
//                .item(vodka)
//                .orderPrice(vodka.getPrice())
//                .quantity(vodkaOrderQuantity)
//                .unit(Unit.PIECE)
//                .sum(vodka.getPrice().multiply(BigDecimal.valueOf(vodkaOrderQuantity)))
//                .build();
//
//        Set<OrderItem> orderItems = Set.of(milkOrder, vodkaOrder);
//
//        BigDecimal totalSum = BigDecimal.ZERO;
//        for (OrderItem oi : orderItems) {
//            totalSum = totalSum.add(oi.getSum());
//        }
//        firstOrder.setTotalSum(totalSum);
//
//        em.getTransaction().begin();
//
//        em.persist(anna);
//        em.persist(milk);
//        em.persist(vodka);
//        em.persist(firstOrder);
//        em.persist(vodkaOrder);
//        em.persist(milkOrder);
//
//        milkId = milk.getId();
//        em.getTransaction().commit();
//        long userId=anna.getId();
//        em.close();
//
//        //****************
//        em = entityManagerFactory.createEntityManager();
//        EntityGraph graph = em.getEntityGraph("graph.order.items");
//        Map hints = Map.of("jakarta.persistence.fetchgraph", graph);
//
//        Order myorder = em.find(Order.class, firstOrder.getId(), hints);
////        System.out.println(myorder);
//
//        Item item1 = em.find(Item.class, milkId);
//        Item item2 = em.find(Item.class, milkId);
//        System.out.println(item1 == item2);
//        System.out.println(item1);
//        em.close();
//
//        em=entityManagerFactory.createEntityManager();
//        anna=em.find(User.class, 1L);
//        System.out.println(anna);
//        Order annaBasket=new Order();
//        annaBasket.setNumber("basket");
//        annaBasket.setUser(anna);
//        annaBasket.setTotalSum(BigDecimal.valueOf(11L));
//        em.getTransaction().begin();
//        em.persist(annaBasket);
//
////        em.persist(annaBasket);
////        anna.setBasket(annaBasket);
////        em.persist(anna);
//        em.getTransaction().commit();
//        em.close();
//        entityManagerFactory.close();
    }
}
