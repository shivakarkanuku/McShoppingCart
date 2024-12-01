package com.McDiffyStore.store.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/seller/")
public class SellerController {


    @GetMapping("/product")
    public String getAllSellerProducts(){
        return "inside of getAllSellerProducts";
    }

}
