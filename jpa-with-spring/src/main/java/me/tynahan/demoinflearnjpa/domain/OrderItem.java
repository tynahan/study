package me.tynahan.demoinflearnjpa.domain;

import lombok.Getter;
import lombok.Setter;
import me.tynahan.demoinflearnjpa.domain.item.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량

    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.decreaseStock(count);
        return orderItem;
    }

    public void cancel() {
        getItem().increaseStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
