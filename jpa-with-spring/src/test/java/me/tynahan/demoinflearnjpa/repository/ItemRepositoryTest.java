package me.tynahan.demoinflearnjpa.repository;

import me.tynahan.demoinflearnjpa.domain.item.Album;
import me.tynahan.demoinflearnjpa.domain.item.Item;
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
@Transactional
@ActiveProfiles("test")
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testSaveItem() {
        // Given
        Item album = new Album();
        itemRepository.save(album);

        // When
        Item findAlbum = itemRepository.findOne(album.getId());

        // Then
        Assertions.assertThat(findAlbum).isEqualTo(album);
    }

    @Test
    public void testFindAllItem() {
        // Given
        int size = itemRepository.findAll().size();
        Item album = new Album();
        itemRepository.save(album);

        // When
        int findSize = itemRepository.findAll().size();

        // Then
        Assertions.assertThat(size + 1).isEqualTo(findSize);
    }

}