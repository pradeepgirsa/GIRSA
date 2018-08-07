package za.co.global.domain.product;

import za.co.global.domain.EntityStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 5500456840911177632L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", unique = true, nullable = false)
    private String productName;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productName != null ? !productName.equals(product.productName) : product.productName != null) return false;
        if (status != product.status) return false;
        return description != null ? description.equals(product.description) : product.description == null;
    }

    @Override
    public int hashCode() {
        int result = productName != null ? productName.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
