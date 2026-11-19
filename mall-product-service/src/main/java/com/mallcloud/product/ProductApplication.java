package com.mallcloud.product;

import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient @SpringBootApplication(scanBasePackages="com.mallcloud")
public class ProductApplication { public static void main(String[] args) { SpringApplication.run(ProductApplication.class, args); } }
