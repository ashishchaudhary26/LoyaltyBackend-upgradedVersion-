package com.service.impl;

import com.dto.*;
import com.entity.*;
import com.mapper.ProductMapper;
import com.repository.*;
import com.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final ProductStockRepository productStockRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductReviewRepository productReviewRepository;
    private final BrandsRepository brandsRepository;
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public ProductServiceImpl(
            ProductsRepository productsRepository,
            ProductStockRepository productStockRepository,
            ProductImageRepository productImageRepository,
            ProductReviewRepository productReviewRepository,
            BrandsRepository brandsRepository,
            CategoriesRepository categoriesRepository) {
        this.productsRepository = productsRepository;
        this.productStockRepository = productStockRepository;
        this.productImageRepository = productImageRepository;
        this.productReviewRepository = productReviewRepository;
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
    }

    private List<Product_Images> loadMainImageList(Long productId) {
        Product_Images main = productImageRepository
                .findFirstByProductIdOrderBySortOrderAscIdAsc(productId);
        if (main == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(main);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> listAvailable(Pageable pageable) {
        Page<Products> page = productsRepository.findByIsAvailableTrue(pageable);

        // Use lambda instead of ProductMapper::toProductDto (because signature changed)
        return page.map(p -> {
            List<Product_Images> mainImageList = loadMainImageList(p.getId());
            return ProductMapper.toProductDto(p, mainImageList);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> search(Long categoryId, Long brandId,
            BigDecimal minPrice, BigDecimal maxPrice,
            String keyword,
            Pageable pageable) {
        Page<Products> page = productsRepository.searchProducts(
                categoryId, brandId, minPrice, maxPrice,
                (keyword == null || keyword.isBlank()) ? null : keyword,
                pageable);

        return page.map(p -> {
            List<Product_Images> mainImageList = loadMainImageList(p.getId());
            return ProductMapper.toProductDto(p, mainImageList);
        });
    }

    @Override
    public ProductDetailDto getProductDetails(Long id) {
        Products p = productsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Product_Stock stock = productStockRepository
                .findByProductId(id)
                .orElse(null);

        // all images, sorted in repository
        List<Product_Images> images = productImageRepository
                .findByProductIdOrderBySortOrderAscIdAsc(id);

        return ProductMapper.toDetailDto(p, stock, images);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductBasic(Long productId) {
        Products p = productsRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // you can decide: only main image or all images
        List<Product_Images> mainImageList = loadMainImageList(productId);
        return ProductMapper.toProductDto(p, mainImageList);
    }

    @Override
    public ProductDto createProduct(CreateProductRequest req) {

        // validate brand/category
        if (req.getBrandId() != null &&
                !brandsRepository.existsById(req.getBrandId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid brandId");
        }

        if (req.getCategoryId() != null &&
                !categoriesRepository.existsById(req.getCategoryId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid categoryId");
        }

        // 1) Map DTO -> entity
        Products entity = ProductMapper.fromCreateRequest(req);

        // 2) Save product first (so it gets an ID)
        Products saved = productsRepository.save(entity);

        // 3) Initialize stock row
        Product_Stock stock = new Product_Stock();
        stock.setProduct(saved);
        stock.setAvailableQuantity(0);
        stock.setReservedQuantity(0);
        productStockRepository.save(stock);

        // 4) Return DTO for list views (no images yet)
        return ProductMapper.toProductDto(saved, Collections.emptyList());
    }

    @Override
    public ProductDto updateProduct(Long productId, UpdateProductRequest req) {

        System.out.println("-------------------update service called");
        Products existing = productsRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // validate brand/category
        if (req.getBrandId() != null && !brandsRepository.existsById(req.getBrandId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brand not found");
        }
        if (req.getCategoryId() != null && !categoriesRepository.existsById(req.getCategoryId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }

        ProductMapper.applyUpdate(existing, req);
        Products updated = productsRepository.save(existing);

        List<Product_Images> mainImageList = loadMainImageList(updated.getId());
        return ProductMapper.toProductDto(updated, mainImageList);
    }

    @Override
    public void softDeleteProduct(Long productId) {
        Products p = productsRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        p.setAvailable(false);
        productsRepository.save(p);
    }

    @Override
    public ProductStockDto updateStock(Long productId, UpdateStockRequest req) {
        Product_Stock stock = productStockRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product stock not found"));

        stock.setAvailableQuantity(req.getAvailableQuantity());
        Product_Stock saved = productStockRepository.save(stock);
        return ProductMapper.toStockDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandDto> listBrands() {
        return brandsRepository.findAll().stream()
                .map(b -> {
                    BrandDto d = new BrandDto();
                    d.setId(b.getId());
                    d.setBrandName(b.getBrandName());
                    d.setDescription(b.getDescription());
                    return d;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> listCategories() {
        return categoriesRepository.findAll().stream()
                .map(c -> {
                    CategoryDto d = new CategoryDto();
                    d.setId(c.getId());
                    d.setCategoryName(c.getCategoryName());
                    return d;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductImageDto> getProductImages(Long productId) {
        List<Product_Images> images = productImageRepository.findByProductId(productId);
        return images.stream().map(ProductMapper::toImageDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductReviewDto> getProductReviews(Long productId) {
        List<Product_Reviews> reviews = productReviewRepository.findByProductId(productId);
        return reviews.stream().map(r -> {
            ProductReviewDto dto = new ProductReviewDto();
            dto.setId(r.getId());
            dto.setUserId(r.getUserId());
            dto.setRating(r.getRating());
            dto.setReviewTitle(r.getReviewTitle());
            dto.setReviewText(r.getReviewText());
            dto.setCreatedAt(r.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductReviewDto addProductReview(Long productId, Long userId, CreateReviewRequest req) {
        // ensure product exists
        Products p = productsRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Product_Reviews r = new Product_Reviews();
        r.setProduct(p);
        r.setUserId(userId);
        r.setRating(req.getRating());
        r.setReviewTitle(req.getReviewTitle());
        r.setReviewText(req.getReviewText());
        Product_Reviews saved = productReviewRepository.save(r);

        ProductReviewDto dto = new ProductReviewDto();
        dto.setId(saved.getId());
        dto.setUserId(saved.getUserId());
        dto.setRating(saved.getRating());
        dto.setReviewTitle(saved.getReviewTitle());
        dto.setReviewText(saved.getReviewText());
        dto.setCreatedAt(saved.getCreatedAt());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductBySku(String sku) {
        return productsRepository.findBySku(sku)
                .map(p -> {
                    List<Product_Images> mainImageList = loadMainImageList(p.getId());
                    return ProductMapper.toProductDto(p, mainImageList);
                })
                .orElse(null);
    }

    @Override
    public ProductImageDto addProductImage(Long productId, ProductImageDto dto) {
        Products p = productsRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Product_Images img = new Product_Images();
        img.setProduct(p);
        img.setImageKey(dto.getImageKey());
        img.setImageUrl(dto.getImageUrl());
        img.setAltText(dto.getAltText());
        img.setSortOrder(dto.getSortOrder());
        Product_Images saved = productImageRepository.save(img);
        return ProductMapper.toImageDto(saved);
    }

    @Override
    public void deleteProductImage(Long productId, Long imageId) {
        Product_Images img = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
        if (img.getProduct() == null || !img.getProduct().getId().equals(productId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image does not belong to product");
        }
        productImageRepository.deleteById(imageId);
    }

    @Override
    public ProductStockDto getStock(Long productId) {
        Product_Stock stock = productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Stock not found for product " + productId));

        ProductStockDto dto = new ProductStockDto();
        dto.setProductId(stock.getProductId());
        dto.setAvailableQuantity(stock.getAvailableQuantity());
        dto.setReservedQuantity(stock.getReservedQuantity());
        return dto;
    }

    @Override
    public void reserveStock(Long productId, Integer qty) {
        Product_Stock stock = productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Stock not found for product " + productId));

        if (qty == null || qty <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid qty");
        }

        if (stock.getAvailableQuantity() < qty) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough stock");
        }

        stock.setAvailableQuantity(stock.getAvailableQuantity() - qty);
        stock.setReservedQuantity(stock.getReservedQuantity() + qty);
        stock.setUpdatedAt(LocalDateTime.now());
        productStockRepository.save(stock);
    }

    @Override
    public void commitStock(Long productId, Integer qty) {
        Product_Stock stock = productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Stock not found for product " + productId));

        if (qty == null || qty <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid qty");
        }

        if (stock.getReservedQuantity() < qty) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough reserved stock");
        }

        stock.setReservedQuantity(stock.getReservedQuantity() - qty);
        stock.setUpdatedAt(LocalDateTime.now());
        productStockRepository.save(stock);
    }

    @Override
    public void releaseStock(Long productId, Integer qty) {
        Product_Stock stock = productStockRepository.findByProductId(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Stock not found for product " + productId));

        if (qty == null || qty <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid qty");
        }

        if (stock.getReservedQuantity() < qty) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough reserved stock to release");
        }

        stock.setReservedQuantity(stock.getReservedQuantity() - qty);
        stock.setAvailableQuantity(stock.getAvailableQuantity() + qty);
        stock.setUpdatedAt(LocalDateTime.now());
        productStockRepository.save(stock);
    }

    @Override
    public CategoryDto createCategory(CategoryDto dto) {
        if (dto.getCategoryName() == null || dto.getCategoryName().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category name is required");
        }

        Categories c = new Categories();
        c.setCategoryName(dto.getCategoryName().trim());

        Categories saved = categoriesRepository.save(c);

        CategoryDto out = new CategoryDto();
        out.setId(saved.getId());
        out.setCategoryName(saved.getCategoryName());
        return out;
    }

    @Override
    public BrandDto createBrand(BrandDto dto) {
        if (dto.getBrandName() == null || dto.getBrandName().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Brand name is required");
        }

        Brands b = new Brands();
        b.setBrandName(dto.getBrandName().trim());
        b.setDescription(dto.getDescription()); // can be null

        Brands saved = brandsRepository.save(b);

        BrandDto out = new BrandDto();
        out.setId(saved.getId());
        out.setBrandName(saved.getBrandName());
        out.setDescription(saved.getDescription());
        return out;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setProductName(product.getProductName());
        dto.setShortDescription(product.getShortDescription());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAvailable(product.isAvailable());
        dto.setBrandId(product.getBrandId());
        dto.setCategoryId(product.getCategoryId());

        dto.setRewardEnabled(product.getRewardEnabled());
        dto.setRewardPercentage(product.getRewardPercentage());

        return dto;
    }

    // reward
    @Override
    public void updateReward(Long productId, Boolean enabled, Double percentage) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setRewardEnabled(enabled);
        product.setRewardPercentage(enabled ? percentage : 0.0);

        productsRepository.save(product);
    }

}
