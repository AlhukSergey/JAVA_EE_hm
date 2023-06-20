package by.teachmeskills.shop.domain;

/*import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int categoryId;
    private String imagePath;
}*/

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int categoryId;
    private String imagePath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getImagePath() {
        return imagePath;
    }

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.categoryId = builder.categoryId;
        this.imagePath = builder.imagePath;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private String name;
        private String description;
        private double price;
        private int categoryId;
        private String imagePath;

        private Builder() {
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder withCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder withImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}