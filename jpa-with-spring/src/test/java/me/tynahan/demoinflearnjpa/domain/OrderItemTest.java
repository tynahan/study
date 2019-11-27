 package me.tynahan.demoinflearnjpa.domain;

import me.tynahan.demoinflearnjpa.domain.item.Album;
import me.tynahan.demoinflearnjpa.domain.item.Item;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemTest {

    @Test
    public void testCreateOrderItem() {
        // Given
        Item item = new Album();
        int stockQuantity = 10;
        item.setStockQuantity(stockQuantity);
        int orderPrice = 1000;
        int count = 2;
        // When
        OrderItem orderItem = OrderItem.createOrderItem(item, orderPrice, count);

        // Then
        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getItem()).isNotNull();
        assertThat(orderItem.getCount()).isEqualTo(count);
        assertThat(orderItem.getOrderPrice()).isEqualTo(orderPrice);
        assertThat(orderItem.getItem().getStockQuantity()).isEqualTo(stockQuantity - count);
    }
    @Test
    public void testCancel() {
        // Given
        OrderItem orderItem = new OrderItem();
        Item item = new Album();
        int stockQuantity = 1;
        item.setStockQuantity(stockQuantity);
        orderItem.setItem(item);

        // When
        orderItem.cancel();

        // Then
        assertThat(orderItem.getItem().getStockQuantity()).isEqualTo(stockQuantity + orderItem.getCount());
    }
}