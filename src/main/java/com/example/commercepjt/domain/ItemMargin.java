package com.example.commercepjt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ITEM_MARGIN")
@Getter
@Setter
public class ItemMargin extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_MARGIN_ID")
    private Long id;

    private String marginRate;
}
