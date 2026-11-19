package com.mallcloud.api.message;

import com.mallcloud.api.product.ProductSnapshot;

public record ProductChangedEvent(String messageId, String operation, ProductSnapshot product) {}
