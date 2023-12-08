package com.example.plusone.discount.mapper;

import com.example.plusone.discount.dto.ProductDto;
import com.example.plusone.discount.entity.Product;
import com.example.plusone.discount.service.DiscountService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DiscountMapper {


    DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);



    ProductDto toDTO(Product entity);
    List<ProductDto> toDTOs(List<Product> entities);

    Product toEntity(ProductDto dto);
    List<Product> toEntities (List<ProductDto> DTOs);
}
