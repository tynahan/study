package me.tynahan.demoinflearnjpa.repository;

import me.tynahan.demoinflearnjpa.domain.*;
import me.tynahan.demoinflearnjpa.domain.item.Album;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testFindOrderByCond() {
        // Given
        //?
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("");

        // When
        List<Order> orders = orderRepository.findOrderByCond(orderSearch);

        // Then
    }

}