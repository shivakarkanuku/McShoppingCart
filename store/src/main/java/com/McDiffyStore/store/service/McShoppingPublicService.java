package com.McDiffyStore.store.service;

import com.McDiffyStore.store.dto.CategoryDto;
import com.McDiffyStore.store.dto.ProductDto;
import com.McDiffyStore.store.entity.Product;
import com.McDiffyStore.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class McShoppingPublicService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductDto> searchProduct(String keyword){

        List<Product> products = productRepository.findAll();


        List<ProductDto> productDtos=new ArrayList<>();

        for(Product product:products){
            if((product.getProductName()).toLowerCase().contains(keyword.toLowerCase()) || product.getCategory().getCategoryName().contains(keyword)){
                ProductDto productDto=new ProductDto();
                productDto.setProductId(product.getProductId());
                productDto.setProductName(product.getProductName());
                productDto.setPrice(product.getPrice());
                CategoryDto categoryDto=new CategoryDto();
                categoryDto.setCategoryName(product.getCategory().getCategoryName());
                productDto.setCategory(categoryDto);
                productDtos.add(productDto);
            }
        }
        return productDtos;
    }
}
