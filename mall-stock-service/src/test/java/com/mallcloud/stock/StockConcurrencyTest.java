package com.mallcloud.stock;

import com.mallcloud.stock.entity.StockEntity;
import com.mallcloud.stock.mapper.StockMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        "spring.cloud.nacos.discovery.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:stocktest;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.sql.init.mode=never"
})
class StockConcurrencyTest {
    @Autowired private JdbcTemplate jdbc;
    @Autowired private StockMapper stockMapper;

    @BeforeEach
    void prepare() {
        jdbc.execute("DROP TABLE IF EXISTS stock");
        jdbc.execute("CREATE TABLE stock (id BIGINT PRIMARY KEY, product_id BIGINT NOT NULL UNIQUE, total_stock INT NOT NULL, available_stock INT NOT NULL, locked_stock INT NOT NULL, version INT NOT NULL, update_time TIMESTAMP NOT NULL)");
        jdbc.update("INSERT INTO stock VALUES (1,1001,100,100,0,0,CURRENT_TIMESTAMP)");
    }

    @Test
    void twoHundredConcurrentRequestsCannotOversellOneHundredItems() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(32);
        CountDownLatch ready = new CountDownLatch(200);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            futures.add(pool.submit(() -> {
                ready.countDown();
                start.await();
                return stockMapper.reserve(1001L, 1);
            }));
        }
        ready.await(10, TimeUnit.SECONDS);
        start.countDown();
        int success = 0;
        for (Future<Integer> future : futures) success += future.get(10, TimeUnit.SECONDS);
        pool.shutdownNow();

        StockEntity stock = stockMapper.selectById(1L);
        assertEquals(100, success);
        assertEquals(0, stock.getAvailableStock());
        assertEquals(100, stock.getLockedStock());
    }
}
