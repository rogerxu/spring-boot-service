package com.example.demo.dto;

import org.hibernate.validator.constraints.EAN;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonInclude(Include.NON_NULL)
public class Product {

    @NotNull
    @Size(min = 3, max = 20, message = "sample.api.error.100001|${validatedValue},{min},{max}")
    private String name;

    @Range(min = 1, max = 10, message = "sample.api.error.100010|${validatedValue},{min},{max}")
    private Integer quantity;

    @Min(value = 18, message = "sample.api.error.100011|${validatedValue},{value}")
    private int age;

    @EAN
    private String ean;

    @Size(min = 1, max = 12, message = "bn.networkcatalog.api.error.100031|${validatedValue},{min},{max}")
    @Pattern(regexp = "\\d+")
    private String upc;

    @URL(protocol = "https")
    private String productUrl;

    public Product(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

}
