//package com.controller;
//
//import com.dto.*;
//import com.service.ProductService;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//
//import org.springframework.http.MediaType;
//
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@WebMvcTest(controllers = ProductController.class)
//public class ProductControllerIT {
//
//    @Autowired private MockMvc mockMvc;
//    @Autowired private ObjectMapper objectMapper;
//
//    @MockBean private ProductService productService;
//
//    @Test
//    void listProducts() throws Exception {
//        ProductDto dto = new ProductDto();
//        dto.setId(1L);
//        dto.setSku("SKU123");
//        dto.setProductName("Headphone");
//        dto.setPrice(BigDecimal.valueOf(1999));
//        dto.setAvailable(true);
//
//        Page<ProductDto> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 20), 1);
//
//        Mockito.when(productService.listAvailable(Mockito.any()))
//                .thenReturn(page);
//
//        mockMvc.perform(get("/api/v1/products"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void searchProducts() throws Exception {
//        Page<ProductDto> page = new PageImpl<>(List.of(), PageRequest.of(0, 20), 0);
//
//        Mockito.when(productService.search(
//                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
//                Mockito.any(), Mockito.any()))
//                .thenReturn(page);
//
//        mockMvc.perform(get("/api/v1/products/search"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void productDetails() throws Exception {
//        ProductDetailDto dto = new ProductDetailDto();
//        Mockito.when(productService.getProductDetails(1L)).thenReturn(dto);
//
//        mockMvc.perform(get("/api/v1/products/1"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void productImages() throws Exception {
//        Mockito.when(productService.getProductImages(1L))
//                .thenReturn(List.of());
//
//        mockMvc.perform(get("/api/v1/products/1/images"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void brandList() throws Exception {
//        Mockito.when(productService.listBrands()).thenReturn(List.of());
//
//        mockMvc.perform(get("/api/v1/products/brands"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void categoryList() throws Exception {
//        Mockito.when(productService.listCategories()).thenReturn(List.of());
//
//        mockMvc.perform(get("/api/v1/products/categories"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void productReviews() throws Exception {
//        Mockito.when(productService.getProductReviews(1L)).thenReturn(List.of());
//
//        mockMvc.perform(get("/api/v1/products/1/reviews"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void addReview() throws Exception {
//        CreateReviewRequest req = new CreateReviewRequest();
//        req.setUserId(42L);           
//        req.setRating(5);
//        req.setReviewText("Great product");
//
//        ProductReviewDto dto = new ProductReviewDto();
//
//        Mockito.when(productService.addProductReview(Mockito.eq(1L), Mockito.any()))
//               .thenReturn(dto);
//
//        mockMvc.perform(post("/api/v1/products/1/reviews")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(req)))
//               .andExpect(status().isCreated());
//    }
//
//
//    @Test
//    void createProduct() throws Exception {
//        CreateProductRequest req = new CreateProductRequest();
//        req.setSku("SKU-123");
//        req.setProductName("Laptop");
//        req.setShortDescription("Short desc");
//        req.setDescription("Full description");
//        req.setPrice(BigDecimal.valueOf(59999));
//        req.setIsAvailable(true);
//        req.setBrandId(1L);
//        req.setCategoryId(1L);
//
//        ProductDto dto = new ProductDto();
//
//        Mockito.when(productService.createProduct(Mockito.any()))
//                .thenReturn(dto);
//
//        mockMvc.perform(post("/api/v1/products/admin")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void updateProduct() throws Exception {
//        UpdateProductRequest req = new UpdateProductRequest();
//        req.setProductName("Updated Name");
//        req.setShortDescription("Updated short");
//        req.setDescription("Updated long description");
//        req.setPrice(BigDecimal.valueOf(5499));
//        req.setIsAvailable(true);
//        req.setBrandId(1L);
//        req.setCategoryId(1L);
//
//        ProductDto dto = new ProductDto();
//
//        Mockito.when(productService.updateProduct(Mockito.eq(1L), Mockito.any()))
//                .thenReturn(dto);
//
//        mockMvc.perform(put("/api/v1/products/admin/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void deleteProduct() throws Exception {
//        mockMvc.perform(delete("/api/v1/products/admin/1"))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void updateStock() throws Exception {
//        UpdateStockRequest req = new UpdateStockRequest();
//        req.setAvailableQuantity(20);
//
//        ProductStockDto dto = new ProductStockDto();
//
//        Mockito.when(productService.updateStock(Mockito.eq(1L), Mockito.any()))
//                .thenReturn(dto);
//
//        mockMvc.perform(put("/api/v1/products/admin/1/stock")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void addProductImage() throws Exception {
//        ProductImageDto dto = new ProductImageDto();
//        dto.setImageUrl("http://example.com/a.jpg");
//
//        Mockito.when(productService.addProductImage(Mockito.eq(1L), Mockito.any()))
//                .thenReturn(dto);
//
//        mockMvc.perform(post("/api/v1/products/admin/1/images")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void deleteProductImage() throws Exception {
//        mockMvc.perform(delete("/api/v1/products/admin/1/images/10"))
//                .andExpect(status().isNoContent());
//    }
//}
