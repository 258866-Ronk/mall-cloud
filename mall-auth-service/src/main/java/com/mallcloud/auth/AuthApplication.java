package com.mallcloud.auth;

import com.mallcloud.api.client.UserClient;
import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(clients = UserClient.class)
@SpringBootApplication(scanBasePackages = "com.mallcloud")
public class AuthApplication { public static void main(String[] args) { SpringApplication.run(AuthApplication.class, args); } }
