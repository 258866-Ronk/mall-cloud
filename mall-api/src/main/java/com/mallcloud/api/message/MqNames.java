package com.mallcloud.api.message;

public final class MqNames {
    public static final String BUSINESS_EXCHANGE = "mall.business.exchange";
    public static final String PRODUCT_CHANGED_QUEUE = "mall.product.changed.queue";
    public static final String PRODUCT_CHANGED_KEY = "product.changed";
    public static final String PAYMENT_SUCCEEDED_QUEUE = "mall.payment.succeeded.queue";
    public static final String PAYMENT_SUCCEEDED_KEY = "payment.succeeded";
    public static final String SECKILL_ORDER_QUEUE = "mall.seckill.order.queue";
    public static final String SECKILL_ORDER_KEY = "seckill.order";
    private MqNames() {}
}
