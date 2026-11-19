package com.mallcloud.admin;
import com.mallcloud.admin.client.*; import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.cloud.client.discovery.EnableDiscoveryClient; import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableDiscoveryClient @EnableFeignClients(clients={UserAdminClient.class,ProductAdminClient.class,OrderAdminClient.class,StockAdminClient.class}) @SpringBootApplication(scanBasePackages="com.mallcloud")
public class AdminApplication {public static void main(String[] args){SpringApplication.run(AdminApplication.class,args);}}
