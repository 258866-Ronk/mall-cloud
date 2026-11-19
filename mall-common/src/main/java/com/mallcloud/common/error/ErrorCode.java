package com.mallcloud.common.error;

public enum ErrorCode {
    BAD_REQUEST(400, "请求参数错误"), UNAUTHORIZED(401, "请先登录"), FORBIDDEN(403, "无权访问"),
    NOT_FOUND(404, "资源不存在"), CONFLICT(409, "数据冲突"), USER_EXISTS(1001, "用户名或手机号已存在"),
    LOGIN_FAILED(1002, "用户名或密码错误"), PRODUCT_UNAVAILABLE(2001, "商品不存在或已下架"),
    STOCK_NOT_ENOUGH(3001, "库存不足"), DUPLICATE_REQUEST(4001, "请勿重复提交"),
    ORDER_STATE_INVALID(4002, "订单状态不允许该操作"), PAYMENT_DUPLICATE(5001, "订单已支付"),
    SECKILL_FAILED(6001, "秒杀失败"), INTERNAL_ERROR(500, "系统繁忙，请稍后重试");
    private final int code; private final String message;
    ErrorCode(int code, String message) { this.code = code; this.message = message; }
    public int code() { return code; } public String message() { return message; }
}
