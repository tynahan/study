package me.tynahan.demoinflearnjpa.repository;

import me.tynahan.demoinflearnjpa.domain.Order;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    public void testSaveOrder() {
        // Given
        Order order = null;

        // When
        orderRepository.save(order);
        Order findOrder = orderRepository.findOne(order.getId());

        // Then
        Assertions.assertThat(findOrder).isEqualTo(order);
    }
}