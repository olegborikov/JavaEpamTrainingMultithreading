package com.borikov.task5.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seaport {
    private static Seaport instance;
    private static final int AMOUNT_OF_PIERS = 3;
    private List<Pier> freePiers;
    private List<Pier> busyPiers;
    private static Lock lock = new ReentrantLock();

    private Seaport() {
        busyPiers = new ArrayList<>();
        freePiers = new ArrayList<>();
    }

    public static Seaport getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new Seaport();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }
}
