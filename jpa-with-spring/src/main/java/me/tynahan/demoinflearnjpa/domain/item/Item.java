package me.tynahan.demoinflearnjpa.domain.item;

import lombok.Getter;
import lombok.Setter;
import me.tynahan.demoinflearnjpa.domain.Category;
import me.tynahan.demoinflearnjpa.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void increaseStock(int quantity) {
        this.stockQuantity += quantity;
    };

    public void decreaseStock(int quantity) {
        int restQuantity = this.stockQuantity - quantity;
        if (restQuantity < 0) {
            throw new NotEnoughStockException("need more stockQuantity");
        }
        this.stockQuantity = restQuantity;
    }
}
