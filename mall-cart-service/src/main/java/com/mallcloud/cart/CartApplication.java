package com.mallcloud.cart;
import com.mallcloud.api.client.ProductClient; import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.cloud.client.discovery.EnableDiscoveryClient; import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableDiscoveryClient @EnableFeignClients(clients=ProductClient.class) @SpringBootApplication(scanBasePackages="com.mallcloud")
public class CartApplication { public static void main(String[] args){SpringApplication.run(CartApplication.class,args);} }
