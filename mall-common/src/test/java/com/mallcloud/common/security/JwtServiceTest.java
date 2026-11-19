package com.mallcloud.common.security;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtServiceTest {
    @Test void createsAndParsesToken() {
        JwtService service = new JwtService(new JwtProperties()); AuthenticatedUser expected = new AuthenticatedUser(7L, "demo", "USER");
        assertEquals(expected, service.parseToken(service.createToken(expected)));
    }
}
