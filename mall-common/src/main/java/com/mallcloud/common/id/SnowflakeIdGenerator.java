package com.mallcloud.common.id;

public class SnowflakeIdGenerator {
    private static final long EPOCH = 1704067200000L; private final long workerId; private long lastTimestamp = -1L; private long sequence;
    public SnowflakeIdGenerator(long workerId) { if (workerId < 0 || workerId > 1023) throw new IllegalArgumentException("workerId must be 0..1023"); this.workerId = workerId; }
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis(); if (timestamp < lastTimestamp) throw new IllegalStateException("System clock moved backwards");
        if (timestamp == lastTimestamp) { sequence = (sequence + 1) & 4095; if (sequence == 0) while ((timestamp = System.currentTimeMillis()) <= lastTimestamp) Thread.onSpinWait(); } else sequence = 0;
        lastTimestamp = timestamp; return ((timestamp - EPOCH) << 22) | (workerId << 12) | sequence;
    }
}
