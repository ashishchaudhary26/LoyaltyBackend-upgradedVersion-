package com.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "categories")
@Schema(name = "Category", description = "Represents a product category")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the category", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;

    @Column(name = "category_name", nullable = false, length = 255)
    @Schema(description = "Name of the category", example = "Electronics")
    public String categoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Schema(description = "List of products belonging to this category")
    public List<Products> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, id, products);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Categories other = (Categories) obj;
        return Objects.equals(categoryName, other.categoryName) && Objects.equals(id, other.id)
                && Objects.equals(products, other.products);
    }

    @Override
    public String toString() {
        return "Categories [id=" + id + ", categoryName=" + categoryName + ", products=" + products + "]";
    }
}
