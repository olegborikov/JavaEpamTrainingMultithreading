package com.borikov.task5.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seaport {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Seaport instance;
    private static final int AMOUNT_OF_PIERS = 3;
    private final int capacity = 10;
    private AtomicInteger fullness;
    private final List<Pier> freePiers = new ArrayList<>();
    private final List<Pier> busyPiers = new ArrayList<>();
    private static final Lock lock = new ReentrantLock();
    private static final Lock pierLock = new ReentrantLock();
    private static final Lock seaportLock = new ReentrantLock();
    private static final Condition pierCondition = pierLock.newCondition();

    private Seaport() {
        fullness = new AtomicInteger();
        for (int i = 0; i < AMOUNT_OF_PIERS; i++) {
            Pier pier = new Pier();
            freePiers.add(pier);
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public AtomicInteger getFullness() {
        return fullness;
    }

    public List<Pier> getFreePiers() {
        return Collections.unmodifiableList(freePiers);
    }

    public List<Pier> getBusyPiers() {
        return Collections.unmodifiableList(busyPiers);
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
            pierLock.lock();
            while (freePiers.isEmpty()) {
                try {
                    pierCondition.await();
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
            pierLock.unlock();
        }
    }

    public void releasePier(Pier pier) {
        try {
            pierLock.lock();
            if (busyPiers.remove(pier)) {
                freePiers.add(pier);
            }
        } finally {
            pierCondition.signal();
            pierLock.unlock();
        }
    }

    public void loadShip(Ship ship) {
        LOGGER.log(Level.INFO, "Ship № {} is loading", ship.getShipId());
        while (!ship.isFull()) {
            try {
                seaportLock.lock();
                if (fullness.get() > 0) {
                    ship.addContainer();
                    fullness.decrementAndGet();
                }
                LOGGER.log(Level.INFO, "Seaport capacity: {}, fullness: {}", capacity, fullness);
                if (fullness.get() == 0) {
                    LOGGER.log(Level.INFO, "Seaport has no containers left");
                    break;
                }
            } finally {
                seaportLock.unlock();
            }
        }
    }

    public void unloadShip(Ship ship) {
        LOGGER.log(Level.INFO, "Ship № {} is unloading", ship.getShipId());
        while (!ship.isEmpty()) {
            try {
                seaportLock.lock();
                if (fullness.get() < capacity) {
                    ship.deleteContainer();
                    fullness.incrementAndGet();
                }
                LOGGER.log(Level.INFO, "Seaport capacity: {}, fullness: {}", capacity, fullness);
                if (fullness.get() == capacity) {
                    LOGGER.log(Level.INFO, "Seaport has no more free space left");
                    break;
                }
            } finally {
                seaportLock.unlock();
            }
        }
    }

    @Override
    public int hashCode() {
        int result = capacity;
        result = 31 * result + (fullness != null ? fullness.hashCode() : 0);
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
