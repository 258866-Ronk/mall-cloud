package com.mallcloud.stock;
import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient @SpringBootApplication(scanBasePackages="com.mallcloud") public class StockApplication { public static void main(String[] args) { SpringApplication.run(StockApplication.class,args); } }
