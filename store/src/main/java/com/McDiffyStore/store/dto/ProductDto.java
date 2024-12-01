package com.McDiffyStore.store.dto;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ProductDto {
    private Long productId;
    private String productName;
    private Long price;
    private CategoryDto category;
}
