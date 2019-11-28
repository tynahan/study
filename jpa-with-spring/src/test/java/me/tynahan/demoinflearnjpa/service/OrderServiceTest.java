package me.tynahan.demoinflearnjpa.service;

import me.tynahan.demoinflearnjpa.domain.*;
import me.tynahan.demoinflearnjpa.domain.item.Album;
import me.tynahan.demoinflearnjpa.domain.item.Item;
import me.tynahan.demoinflearnjpa.repository.ItemRepository;
import me.tynahan.demoinflearnjpa.repository.MemberRepository;
import me.tynahan.demoinflearnjpa.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testOrder() {
        // Given
        // 회원 추가
        Member member = new Member();
        member.setName("newbie");
        Address address = new Address("seoul", "sungbuk", "21740");
        member.setAddress(address);
        Long memberId = memberRepository.save(member);

        // 상품 추가
        Item item = new Album();
        int stockQuantity = 10;
        item.setStockQuantity(stockQuantity);
        int price = 1000;
        item.setPrice(price);
        itemRepository.save(item);
        Long itemId = item.getId();

        // 주문 수량
        int count = 1;

        // When
        Long orderedId = orderService.order(memberId, itemId, count);
        Order findOrder = orderRepository.findOne(orderedId);

        // Then
        assertThat(findOrder.getMember()).isNotNull();
        assertThat(findOrder.getOrderItems()).isNotNull();
        assertThat(findOrder.getDelivery()).isNotNull();
        assertThat(findOrder.getOrderItems().get(0).getId()).isNotNull();
        assertThat(findOrder.getDelivery().getAddress()).isNotNull();
        assertThat(findOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(findOrder.getId()).isNotNull();
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(stockQuantity - count);
        assertThat(findOrder.getOrderItems().get(0).getOrderPrice()).isEqualTo(price * count);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    public void testOrderCancel() {
        // Given
        // 회원 추가
        Member member = new Member();
        member.setName("newbie");
        Address address = new Address("seoul", "sungbuk", "21740");
        member.setAddress(address);
        Long memberId = memberRepository.save(member);

        // 상품 추가
        Item item = new Album();
        int stockQuantity = 10;
        item.setStockQuantity(stockQuantity);
        int price = 1000;
        item.setPrice(price);
        itemRepository.save(item);
        Long itemId = item.getId();

        // 주문 수량
        int count = 1;

        Long orderedId = orderService.order(memberId, itemId, count);

        Order order = orderRepository.findOne(orderedId);
        int stockQuantity1 = order.getOrderItems().get(0).getItem().getStockQuantity();
        int count1 = order.getOrderItems().get(0).getCount();

        // When
        orderService.cancelOrder(orderedId);
        Order findOrder = orderRepository.findOne(orderedId);

        // Then
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(findOrder.getOrderItems().get(0).getItem().getStockQuantity()).isNotEqualTo(stockQuantity1);

    }
}