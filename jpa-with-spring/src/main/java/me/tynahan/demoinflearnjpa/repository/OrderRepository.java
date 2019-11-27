package me.tynahan.demoinflearnjpa.repository;

import lombok.RequiredArgsConstructor;
import me.tynahan.demoinflearnjpa.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }
}
