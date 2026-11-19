package com.mallcloud.gateway;

import com.mallcloud.common.security.AuthenticatedUser;
import com.mallcloud.common.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.cloud.nacos.discovery.enabled=false",
        "spring.cloud.sentinel.enabled=false"
})
@AutoConfigureWebTestClient
class GatewaySecurityTest {
    @Autowired private WebTestClient client;
    @Autowired private JwtService jwtService;

    @Test
    void healthEndpointIsPublic() {
        client.get().uri("/actuator/health").exchange().expectStatus().isOk();
    }

    @Test
    void cartRequiresAuthentication() {
        client.get().uri("/api/cart").exchange().expectStatus().isUnauthorized()
                .expectBody().jsonPath("$.code").isEqualTo(401);
    }

    @Test
    void normalUserCannotAccessAdminApi() {
        String token = jwtService.createToken(new AuthenticatedUser(7L, "demo", "USER"));
        client.get().uri("/api/admin/users")
                .header("Authorization", "Bearer " + token)
                .exchange().expectStatus().isForbidden()
                .expectBody().jsonPath("$.code").isEqualTo(403);
    }
}
