package ru.mirea.MeatMarket.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    public Product(String name, double price, double mass, String category) {
        this.name = name;
        this.price = price;
        this.mass = mass;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(mass, product.mass) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, mass, category);
    }
}
