package com.McDiffyStore.store.controller;

import com.McDiffyStore.store.entity.Cart;
import com.McDiffyStore.store.entity.CartProduct;
import com.McDiffyStore.store.entity.McUser;
import com.McDiffyStore.store.entity.Product;
import com.McDiffyStore.store.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/consumer")
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/cart")
    public Cart getCart(){

        return consumerService.getAllConsumerCart();

    }
    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody Product product){

        return consumerService.addProductToCart(product);
    }

    @PutMapping("/cart")
    public ResponseEntity<?> updateCartProduct(@RequestBody CartProduct cartProduct){

        return consumerService.updateCartProduct(cartProduct);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> deleteCartProduct(@RequestBody Product product){
        return consumerService.deleteCartProduct(product);
    }



}
