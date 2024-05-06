package ru.mirea.MeatMarket.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "users_seq", sequenceName =
            "users_sequence", allocationSize = 1)
    @GeneratedValue(generator = "users_seq", strategy =
            GenerationType.SEQUENCE)
    long id;

    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "mass")
    private double mass;
    @Column(name = "category")
    private String category;
}
