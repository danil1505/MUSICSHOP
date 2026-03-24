package com.guitarstore.guitar_store_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(
        pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
public class GuitarStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuitarStoreApplication.class, args);
    }
}