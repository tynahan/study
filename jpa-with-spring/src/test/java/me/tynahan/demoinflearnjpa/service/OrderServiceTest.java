package me.tynahan.demoinflearnjpa.service;

import me.tynahan.demoinflearnjpa.domain.*;
import me.tynahan.demoinflearnjpa.domain.item.Album;
import me.tynahan.demoinflearnjpa.domain.item.Item;
import me.tynahan.demoinflearnjpa.exception.NotEnoughStockException;
import me.tynahan.demoinflearnjpa.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testOrder() {
        // Given
        // 회원 추가
        Member member = createMember();

        // 상품 추가
        int stockQuantity = 10;
        int price = 1000;
        Item item = createAlbum(stockQuantity, price);

        // When
        // 주문 수량
        int count = 1;
        Long orderedId = orderService.order(member.getId(), item.getId() , count);

        // Then
        Order findOrder = orderRepository.findOne(orderedId);

        assertThat(findOrder.getMember()).isNotNull();
        assertThat(findOrder.getOrderItems()).isNotNull();
        assertThat(findOrder.getDelivery()).isNotNull();
        assertThat(findOrder.getOrderItems()).filteredOn("id", not(null));
        assertThat(findOrder.getDelivery().getAddress()).isNotNull();
        assertThat(findOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(findOrder.getId()).isNotNull();

        assertThat(findOrder.getStatus())
                .as("상품 주문시 상태는 ORDER")
                .isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size())
                .as("주문한 상품 종류 수가 정확해야 한다")
                .isEqualTo(1);
        assertThat(findOrder.getTotalPrice())
                .as("주문 가격은 가격 * 주문 수량이다")
                .isEqualTo(price * count);
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity())
                .as("주문 수량만큼 재고가 줄어야 한다")
                .isEqualTo(stockQuantity - count);
    }



    @Test(expected = NotEnoughStockException.class)
    public void testOrderOverStockException() {
        // Given
        // 회원 추가
        Member member = createMember();

        // 상품 추가
        int stockQuantity = 1;
        int price = 1000;
        Item item = createAlbum(stockQuantity, price);

        // When
        // 주문 수량
        int count = 10;
        Long orderedId = orderService.order(member.getId(), item.getId() , count);

        // Then
        fail("Occure Not Enough Stock Exception");

    }


    @Test
    public void testOrderCancel() {
        // Given
        // 회원 추가
        Member member = createMember();

        // 상품 추가
        int stockQuantity = 10;
        int price = 1000;
        Item item = createAlbum(stockQuantity, price);

        // 주문 수량
        int count = 1;
        Long orderedId = orderService.order(member.getId(), item.getId() , count);

        // When
        orderService.cancelOrder(orderedId);

        // Then
        Order findOrder = orderRepository.findOne(orderedId);

        assertThat(findOrder.getStatus())
                .as("order cancel status CANCEL").isEqualTo(OrderStatus.CANCEL);
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity())
                .as("order cancel item increase stock").isEqualTo(stockQuantity);

    }


    private Album createAlbum(int stockQuantity, int price) {
        Album album = new Album();
        album.setName("item");
        album.setStockQuantity(stockQuantity);
        album.setPrice(price);
        em.persist(album);
        return album;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("newbie");
        member.setAddress(new Address("seoul", "sungbuk", "21740"));
        em.persist(member);
        return member;
    }
}