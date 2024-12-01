package com.McDiffyStore.store.service;

import com.McDiffyStore.store.dto.CartDto;
import com.McDiffyStore.store.entity.Cart;
import com.McDiffyStore.store.entity.CartProduct;
import com.McDiffyStore.store.entity.McUser;
import com.McDiffyStore.store.entity.Product;
import com.McDiffyStore.store.jwt.JwtUtil;
import com.McDiffyStore.store.repository.CartProductRepository;
import com.McDiffyStore.store.repository.CartRepository;
import com.McDiffyStore.store.repository.ProductRepository;
import com.McDiffyStore.store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsumerService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    JwtUtil jwtUtil;


    public Cart getAllConsumerCart() {
        updateCartTotalAmount();
        return cartRepository.findByUsername(jwtUtil.getCurrentUserUsername());
    }


    public ResponseEntity<?> addProductToCart(Product product) {

        Cart cart=getAllConsumerCart();
        if(cart.getCartProduct().stream()
                .anyMatch(cp-> cp.getProduct().getProductId().equals(product.getProductId()))) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else {
            Product product1=productRepository.findById(product.getProductId()).get();
            CartProduct cartProduct=new CartProduct();
            cartProduct.setProduct(product1);
            cartProduct.setQuantity(1L);
            cartProduct.setCart(cart);
            cartProductRepository.save(cartProduct);
            updateCartTotalAmount();
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    public ResponseEntity<?> updateCartProduct(CartProduct cartProduct) {
        Cart cart=getAllConsumerCart();

        if(cart.getCartProduct().stream()
                .anyMatch(cp-> cp.getProduct().getProductId().equals(cartProduct.getProduct().getProductId()))) {

            for(CartProduct cp:cart.getCartProduct()){
               if(cp.getProduct().getProductId().equals(cartProduct.getProduct().getProductId())){
                   Long cpId=cp.getCpId();

                   CartProduct newCartProduct=new CartProduct();

                   Product product=productRepository.findById(cartProduct.getProduct().getProductId()).get();
                   newCartProduct.setCpId(cpId);
                   newCartProduct.setProduct(product);
                   newCartProduct.setQuantity(cartProduct.getQuantity());
                   newCartProduct.setCart(cart);
                   cartProductRepository.save(newCartProduct);
                   updateCartTotalAmount();
                   return new ResponseEntity<>(HttpStatus.OK);
               }
           }

        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    public void updateCartTotalAmount(){

        Cart cart=cartRepository.findByUsername(jwtUtil.getCurrentUserUsername());
        Long totalAmount=0L;
        for(CartProduct cartProduct:cart.getCartProduct())
        {
           totalAmount= totalAmount+(cartProduct.getProduct().getPrice()*cartProduct.getQuantity());
        }
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

    }

    public ResponseEntity<?> deleteCartProduct(Product product) {
        Cart cart=getAllConsumerCart();
        if(cart.getCartProduct().stream()
                .anyMatch(cp-> cp.getProduct().getProductId().equals(product.getProductId()))) {
        for(CartProduct cartProduct:cart.getCartProduct()){
            if(Objects.equals(cartProduct.getProduct().getProductId(), product.getProductId())){
                if(cartProduct.getQuantity()>1){
                    Long cpId=cartProduct.getCpId();
                    CartProduct newCartProduct=new CartProduct();

                    Product dbProduct=productRepository.findById(cartProduct.getProduct().getProductId()).get();
                    newCartProduct.setCpId(cpId);
                    newCartProduct.setProduct(dbProduct);
                    newCartProduct.setQuantity(cartProduct.getQuantity()-1);
                    newCartProduct.setCart(cart);
                    cartProductRepository.save(newCartProduct);
                    updateCartTotalAmount();
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    cart.getCartProduct().remove(cartProduct);
                    cartProduct.setCart(cart);
                    cartProductRepository.save(cartProduct);
                    updateCartTotalAmount();
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }

        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
