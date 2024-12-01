package com.McDiffyStore.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CartProductsDto {
    private Long cpId;

    private ProductDto product;
    private Long quantity;
}
