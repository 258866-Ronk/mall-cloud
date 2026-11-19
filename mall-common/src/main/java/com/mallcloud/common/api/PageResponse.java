package com.mallcloud.common.api;

import java.util.List;

public record PageResponse<T>(List<T> records, long page, long size, long total, long pages) {
    public static <T> PageResponse<T> of(List<T> records, long page, long size, long total) {
        return new PageResponse<>(records, page, size, total, size == 0 ? 0 : (total + size - 1) / size);
    }
}
