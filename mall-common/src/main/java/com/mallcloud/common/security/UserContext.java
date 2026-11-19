package com.mallcloud.common.security;

import com.mallcloud.common.error.BizException; import com.mallcloud.common.error.ErrorCode;

public final class UserContext {
    private static final ThreadLocal<AuthenticatedUser> CURRENT = new ThreadLocal<>();
    private UserContext() {}
    public static void set(AuthenticatedUser user) { CURRENT.set(user); }
    public static AuthenticatedUser get() { return CURRENT.get(); }
    public static AuthenticatedUser require() { AuthenticatedUser user = CURRENT.get(); if (user == null) throw new BizException(ErrorCode.UNAUTHORIZED); return user; }
    public static void requireAdmin() { if (!require().isAdmin()) throw new BizException(ErrorCode.FORBIDDEN); }
    public static void clear() { CURRENT.remove(); }
}
