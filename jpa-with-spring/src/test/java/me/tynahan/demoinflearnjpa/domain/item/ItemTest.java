package me.tynahan.demoinflearnjpa.domain.item;

import me.tynahan.demoinflearnjpa.exception.NotEnoughStockException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ItemTest {

    @Test
    public void testIncreseStock() {
        // Given
        Item album = new Album();
        int quantity = 1;
        album.setStockQuantity(quantity);

        // When
        int increasedQuantity = 2;
        album.increaseStock(increasedQuantity);

        // Then
        Assertions.assertThat(album.getStockQuantity()).isEqualTo(increasedQuantity + quantity);
    }

    @Test
    public void testDecreaseStock() {
        // Given
        Item album = new Album();
        int quantity = 3;
        album.setStockQuantity(quantity);

        // When
        int decreasedQuantity = 2;
        album.decreaseStock(decreasedQuantity);

        // Then
        Assertions.assertThat(album.getStockQuantity()).isEqualTo(quantity - decreasedQuantity);
    }

    @Test(expected = NotEnoughStockException.class)
    public void testDecreaseStockMinusException() {
        // Given
        Item album = new Album();
        int quantity = 1;
        album.setStockQuantity(quantity);

        // When
        int decreasedQuantity = 2;
        album.decreaseStock(decreasedQuantity);

        // Then
        fail("Occure Not Enough Stock Exception");
    }

}