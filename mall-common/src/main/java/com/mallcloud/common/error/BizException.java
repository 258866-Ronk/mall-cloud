package com.mallcloud.common.error;

public class BizException extends RuntimeException {
    private final int code;
    public BizException(ErrorCode errorCode) { super(errorCode.message()); this.code = errorCode.code(); }
    public BizException(ErrorCode errorCode, String message) { super(message); this.code = errorCode.code(); }
    public int getCode() { return code; }
}
