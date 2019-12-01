package me.tynahan.demoinflearnjpa.service;

import lombok.RequiredArgsConstructor;
import me.tynahan.demoinflearnjpa.domain.*;
import me.tynahan.demoinflearnjpa.domain.item.Item;
import me.tynahan.demoinflearnjpa.repository.ItemRepository;
import me.tynahan.demoinflearnjpa.repository.MemberRepository;
import me.tynahan.demoinflearnjpa.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    public Order findOneOrder(Long orderedId) {
        return null;
    }

    /**
     * 주문 취
     * @param orderId
     */
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }
}
