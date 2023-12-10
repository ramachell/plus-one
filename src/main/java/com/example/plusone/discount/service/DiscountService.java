package com.example.plusone.discount.service;

import com.example.plusone.discount.dto.ProductDto;
import com.example.plusone.discount.dto.SearchDto;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.mapper.DiscountMapper;
import com.example.plusone.discount.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

    private final ProductRepository productRepository;

    public List<ProductDto> productFilter(String filterDiscountType) {
//        log.info(productRepository.findAllByDiscountType(filterDiscountType).toString());
        return DiscountMapper.INSTANCE.toDTOs(productRepository.findAllByDiscountType(filterDiscountType));
    }


    public void putProduct(ProductDto productDto){
        productDto.setId(UUID.randomUUID().toString());
        log.info(productDto.toString());
        productRepository.save(DiscountMapper.INSTANCE.toEntity(productDto));

    }

    public void putProducts(List<ProductDto> productDtoList) {
        List<Product> productList =  DiscountMapper.INSTANCE.toEntities(productDtoList);
        for (Product product : productList) {
            productRepository.save(product);
        }
    }

    public ProductDto getProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return DiscountMapper.INSTANCE.toDTO(product);

    }

    public List<ProductDto> searchProduct(SearchDto searchDto) {

        List<Product> productList = productRepository.findAllByNameContainsAndDiscountType(searchDto.getQuery(),searchDto.getDiscount_type());

        return DiscountMapper.INSTANCE.toDTOs(productList);
    }
}
