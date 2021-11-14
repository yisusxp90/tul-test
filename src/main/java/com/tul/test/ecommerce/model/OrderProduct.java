package com.tul.test.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders_products")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = -8732440989493386815L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional=false)
    @JoinColumn(name="product_fk", nullable=false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProduct)) return false;
        OrderProduct orderProduct = (OrderProduct) o;
        return getId().equals(orderProduct.getId());
    }
}
