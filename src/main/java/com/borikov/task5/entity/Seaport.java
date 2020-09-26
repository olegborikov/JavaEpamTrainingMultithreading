package com.borikov.task5.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seaport {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Seaport instance;
    private static final int AMOUNT_OF_PIERS = 3;
    private static final int DEFAULT_CAPACITY = 10;
    private final int capacity;
    private int fullness;
    private final List<Pier> freePiers = new ArrayList<>();
    private final List<Pier> busyPiers = new ArrayList<>();
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    private Seaport() {
        capacity = DEFAULT_CAPACITY;
        for (int i = 0; i < AMOUNT_OF_PIERS; i++) {
            Pier pier = new Pier();
            freePiers.add(pier);
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFullness() {
        return fullness;
    }

    public void setFullness(int fullness) {
        this.fullness = fullness;
    }

    public static Seaport getInstance() {
        try {
            lock.lock();
            if (instance == null) {
                instance = new Seaport();
                LOGGER.log(Level.INFO, "{} was created", instance);
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public Pier getPier() {
        try {
            lock.lock();
            while (freePiers.isEmpty()) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARN, "Thread was interrupted");
                }
            }
            Optional<Pier> pierOptional = freePiers.stream().findAny();
            Pier pier = pierOptional.get();
            freePiers.remove(pier);
            busyPiers.add(pier);
            return pier;
        } finally {
            lock.unlock();
        }
    }

    public void releasePier(Pier pier) {
        try {
            lock.lock();
            if (busyPiers.remove(pier)) {
                freePiers.add(pier);
            }
        } finally {
            condition.signal();
            lock.unlock();
        }
    }

    public void addContainers(int amountOfContainers) {
        fullness += amountOfContainers;
    }

    public void getContainers(int capacityOfShip) {
        fullness -= capacityOfShip;
    }

    @Override
    public int hashCode() {
        int result = capacity;
        result = 31 * result + fullness;
        result = 31 * result + (freePiers != null ? freePiers.hashCode() : 0);
        result = 31 * result + (busyPiers != null ? busyPiers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Seaport{");
        sb.append("capacity=").append(capacity);
        sb.append(", fullness=").append(fullness);
        sb.append(", freePiers=").append(freePiers);
        sb.append(", busyPiers=").append(busyPiers);
        sb.append('}');
        return sb.toString();
    }
}
