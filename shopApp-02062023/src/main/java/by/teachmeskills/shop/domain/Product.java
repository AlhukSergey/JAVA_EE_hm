package by.teachmeskills.shop.domain;

import lombok.Builder;
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
}