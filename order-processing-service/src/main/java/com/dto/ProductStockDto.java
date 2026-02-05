package com.dto;
public class ProductStockDto {
  public Long productId;
  public Integer availableQuantity;
  public Integer reservedQuantity;
  public Long getProductId() {
	return productId;
  }
  public void setProductId(Long productId) {
	this.productId = productId;
  }
  public Integer getAvailableQuantity() {
	return availableQuantity;
  }
  public void setAvailableQuantity(Integer availableQuantity) {
	this.availableQuantity = availableQuantity;
  }
  public Integer getReservedQuantity() {
	return reservedQuantity;
  }
  public void setReservedQuantity(Integer reservedQuantity) {
	this.reservedQuantity = reservedQuantity;
  }
  
  
}
