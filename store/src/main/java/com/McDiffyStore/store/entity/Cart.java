package com.McDiffyStore.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private Long totalAmount;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    @JsonIgnore
    private McUser user;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id",referencedColumnName = "cartId")
    @JsonIgnoreProperties("cart")
    private List<CartProduct> cartProduct;



}
