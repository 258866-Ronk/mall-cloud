package com.mallcloud.seckill;
import com.mallcloud.api.client.OrderClient; import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.cloud.client.discovery.EnableDiscoveryClient; import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableDiscoveryClient @EnableFeignClients(clients=OrderClient.class) @SpringBootApplication(scanBasePackages="com.mallcloud") public class SeckillApplication {public static void main(String[] args){SpringApplication.run(SeckillApplication.class,args);}}
