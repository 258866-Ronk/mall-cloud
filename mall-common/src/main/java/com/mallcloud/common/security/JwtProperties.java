package com.mallcloud.common.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mall.jwt")
public class JwtProperties {
    private String secret = "bWFsbC1jbG91ZC1kZW1vLXNlY3JldC1rZXktbXVzdC1iZS1sb25nLWVub3VnaA==";
    private long expirationSeconds = 7200;
    public String getSecret() { return secret; } public void setSecret(String secret) { this.secret = secret; }
    public long getExpirationSeconds() { return expirationSeconds; } public void setExpirationSeconds(long expirationSeconds) { this.expirationSeconds = expirationSeconds; }
}
