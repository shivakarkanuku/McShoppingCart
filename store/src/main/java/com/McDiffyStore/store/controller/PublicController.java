package com.McDiffyStore.store.controller;

import com.McDiffyStore.store.dto.LoginDto;
import com.McDiffyStore.store.dto.ProductDto;
import com.McDiffyStore.store.jwt.JwtTokenGenerator;
import com.McDiffyStore.store.service.McShoppingPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private McShoppingPublicService mcShoppingPublicService;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<ProductDto>> searchProduct(@PathVariable String keyword) {
        List<ProductDto> products=mcShoppingPublicService.searchProduct(keyword);
        if(products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginPage(@RequestBody LoginDto loginDto){

        return jwtTokenGenerator.generateToken(loginDto);
    }
}
