package com.borikov.task5.util;

public class IdGenerator {
    private static final long MIN_ID = 1;
    private static final long MAX_ID = 100_000;
    private static long currentId = 0;

    private IdGenerator() {
    }

    public static long generateId() {
        if (currentId + 1 < MAX_ID) {
            currentId++;
        } else {
            currentId = MIN_ID;
        }
        return currentId;
    }
}
