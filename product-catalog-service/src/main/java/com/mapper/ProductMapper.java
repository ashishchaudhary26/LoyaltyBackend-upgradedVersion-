package com.mapper;

import com.dto.*;
import com.entity.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    /**
     * Basic product projection used for list/search API (/api/v1/products, /search).
     * This version accepts images (usually only main image for list).
     */
    public static ProductDto toProductDto(Products p, List<Product_Images> images) {
        if (p == null) return null;

        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setSku(p.getSku());
        dto.setProductName(p.getProductName());
        dto.setShortDescription(p.getShortDescription());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setAvailable(p.isAvailable());
        dto.setBrandId(p.getBrandId());
        dto.setCategoryId(p.getCategoryId());

        if (images != null && !images.isEmpty()) {
            List<ProductImageDto> imageDtos = images.stream()
                    .sorted(Comparator.comparing(
                            img -> img.getSortOrder() == null ? Integer.MAX_VALUE : img.getSortOrder()
                    ))
                    .map(ProductMapper::toImageDto)
                    .collect(Collectors.toList());
            dto.setImages(imageDtos);
        }

        return dto;
    }

    /**
     * Product image entity -> DTO.
     */
    public static ProductImageDto toImageDto(Product_Images img) {
        if (img == null) return null;

        ProductImageDto dto = new ProductImageDto();
        dto.setId(img.getId());
        dto.setImageKey(img.getImageKey());
        dto.setImageUrl(img.getImageUrl());
        dto.setAltText(img.getAltText());
        dto.setSortOrder(img.getSortOrder());
        return dto;
    }

    /**
     * Stock entity -> DTO.
     */
    public static ProductStockDto toStockDto(Product_Stock s) {
        if (s == null) return null;

        ProductStockDto dto = new ProductStockDto();
        dto.setProductId(s.getProductId());
        dto.setAvailableQuantity(s.getAvailableQuantity());
        dto.setReservedQuantity(s.getReservedQuantity());
        return dto;
    }

    /**
     * Full product detail projection, including ALL images.
     */
    public static ProductDetailDto toDetailDto(
            Products p,
            Product_Stock stock,
            List<Product_Images> images
    ) {
        if (p == null) return null;

        ProductDetailDto dto = new ProductDetailDto();
        dto.setId(p.getId());
        dto.setSku(p.getSku());
        dto.setProductName(p.getProductName());
        dto.setShortDescription(p.getShortDescription());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setAvailable(p.isAvailable());
        dto.setBrandId(p.getBrandId());
        dto.setCategoryId(p.getCategoryId());
        dto.setStock(toStockDto(stock));

        if (images != null) {
            List<ProductImageDto> imageDtos = images.stream()
                    .sorted(Comparator.comparing(
                            img -> img.getSortOrder() == null ? Integer.MAX_VALUE : img.getSortOrder()
                    ))
                    .map(ProductMapper::toImageDto)
                    .collect(Collectors.toList());
            dto.setImages(imageDtos);
        }

        return dto;
    }

    /**
     * Map CreateProductRequest -> Products entity.
     */
    public static Products fromCreateRequest(CreateProductRequest req) {
        Products p = new Products();
        p.setSku(req.getSku());
        p.setProductName(req.getProductName());
        p.setShortDescription(req.getShortDescription());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setAvailable(req.getIsAvailable() != null ? req.getIsAvailable() : true);
        p.setBrandId(req.getBrandId());
        p.setCategoryId(req.getCategoryId());
        return p;
    }

    /**
     * Apply UpdateProductRequest onto existing entity.
     */
    public static void applyUpdate(Products existing, UpdateProductRequest req) {
        existing.setProductName(req.getProductName());
        existing.setShortDescription(req.getShortDescription());
        existing.setDescription(req.getDescription());
        existing.setPrice(req.getPrice());

        if (req.getIsAvailable() != null) {
            existing.setAvailable(req.getIsAvailable());
        }

        existing.setBrandId(req.getBrandId());
        existing.setCategoryId(req.getCategoryId());
    }
}
