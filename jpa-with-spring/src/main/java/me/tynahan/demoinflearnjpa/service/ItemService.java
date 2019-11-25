package me.tynahan.demoinflearnjpa.service;

import lombok.RequiredArgsConstructor;
import me.tynahan.demoinflearnjpa.domain.item.Item;
import me.tynahan.demoinflearnjpa.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findOneItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }
}
