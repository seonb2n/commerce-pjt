package com.example.commercepjt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ITEM")
@Getter @Setter
@NoArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SELLER_ID")
    private UserSeller userSeller;

    @OneToMany(mappedBy = "item")
    private List<Comment> commentList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "ITEM_MARGIN_ID")
    private ItemMargin itemMargin;

    private String name;

    private String description;

    private int price;

    private int stockQuantity;

    private boolean isSelling;

    @Builder
    public Item(Long id, Category category, UserSeller userSeller, ItemMargin itemMargin,
        String name,
        String description, int price, int stockQuantity, boolean isSelling) {
        this.id = id;
        this.category = category;
        this.userSeller = userSeller;
        this.itemMargin = itemMargin;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.isSelling = isSelling;
    }

    public int getPrice() {
        return (int) Math.ceil((double) price * Double.parseDouble(this.itemMargin.getMarginRate()));
    }
}
