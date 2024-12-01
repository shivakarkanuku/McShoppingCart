package com.McDiffyStore.store.dto;



import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter@Setter
public class CartDto {
    private Long cartId;
    private Long totalAmount;
    private List<CartProductsDto> cartProducts;

}
