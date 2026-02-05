package com.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "shipping_addresses")
@Schema(name = "ShippingAddress", description = "Shipping address stored for a user")
public class ShippingAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Primary key of the shipping address", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Schema(description = "Identifier of the user who owns this address", example = "42", required = true)
    private Long userId;

    @Column(name = "full_name", nullable = false, length = 255)
    @Schema(description = "Full name of the recipient", example = "John Doe", required = true)
    private String fullName;

    @Column(name = "mobile_number", length = 20)
    @Schema(description = "Contact mobile number for the address", example = "+919876543210")
    private String mobileNumber;

    @Column(name = "address_line", nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Full address line (street, house no., landmark)", example = "123, MG Road, Near Central Park", required = true)
    private String addressLine;

    @Column(nullable = false, length = 100)
    @Schema(description = "City of the shipping address", example = "Bengaluru", required = true)
    private String city;

    @Column(nullable = false, length = 100)
    @Schema(description = "State of the shipping address", example = "Karnataka", required = true)
    private String state;

    @Column(nullable = false, length = 20)
    @Schema(description = "Postal code / PIN code", example = "560001", required = true)
    private String pincode;

    @Column(nullable = false, length = 100)
    @Schema(description = "Country of the shipping address", example = "India", required = true)
    private String country;

    @Column(name = "created_at")
    @Schema(description = "Timestamp when the address was created", example = "2025-11-25T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressLine, city, country, createdAt, fullName, id, mobileNumber, pincode, state, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShippingAddresses other = (ShippingAddresses) obj;
        return Objects.equals(addressLine, other.addressLine) && Objects.equals(city, other.city)
                && Objects.equals(country, other.country) && Objects.equals(createdAt, other.createdAt)
                && Objects.equals(fullName, other.fullName) && Objects.equals(id, other.id)
                && Objects.equals(mobileNumber, other.mobileNumber) && Objects.equals(pincode, other.pincode)
                && Objects.equals(state, other.state) && Objects.equals(userId, other.userId);
    }

    @Override
    public String toString() {
        return "ShippingAddresses [id=" + id + ", userId=" + userId + ", fullName=" + fullName + ", mobileNumber="
                + mobileNumber + ", addressLine=" + addressLine + ", city=" + city + ", state=" + state + ", pincode="
                + pincode + ", country=" + country + ", createdAt=" + createdAt + "]";
    }
}
