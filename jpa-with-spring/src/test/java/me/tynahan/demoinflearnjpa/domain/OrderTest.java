package me.tynahan.demoinflearnjpa.domain;

import me.tynahan.demoinflearnjpa.domain.item.Album;
import me.tynahan.demoinflearnjpa.domain.item.Item;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class OrderTest {

    Order order;

    @Before
    public void setUp() {
        Member member = new Member();
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);
        Item item = new Album();
        item.setStockQuantity(1);
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(1);
        orderItem.setOrderPrice(10000);

        order = Order.createOrder(member, delivery, orderItem);
    }

    @Test
    public void testCreateOrder() {
        // Given & When & Then
        assertThat(order).isNotNull();
        assertThat(order.getOrderItems()).isNotNull();
        assertThat(order.getOrderItems().get(0).getItem()).isNotNull();
        assertThat(order.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(order.getOrderItems().get(0).getCount()).isEqualTo(1);
        assertThat(order.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(1);

    }

    @Test
    public void testOrderCancel() {
        // Given
        OrderItem orderItem = order.getOrderItems().get(0);
        int count = orderItem.getCount();
        int stockQuantity = orderItem.getItem().getStockQuantity();
        // When
        order.cancel();

        // Then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(orderItem.getItem().getStockQuantity()).isEqualTo(count + stockQuantity);

    }

    @Test(expected = IllegalStateException.class)
    public void testOrderCancelException() {
        // Given
        order.getDelivery().setStatus(DeliveryStatus.COMP);

        // When
        order.cancel();

        // Then
        fail("배송 완료된 주문은 취소할 수 없다.");
    }

    @Test
    public void testGetTotalPrice() {
        // Given
        int totalPrice = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            totalPrice += orderItem.getTotalPrice();
        }

        // When
        int findTotalPrice = order.getTotalPrice();

        // Then
        assertThat(findTotalPrice).isEqualTo(totalPrice);

    }
}