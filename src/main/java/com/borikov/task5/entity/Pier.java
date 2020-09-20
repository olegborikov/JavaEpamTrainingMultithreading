package com.borikov.task5.entity;

import com.borikov.task5.util.IdGenerator;

public class Pier {
    private final long pierId;
    private int capacity;
    private int amountOfContainers;

    public Pier(int capacity, int amountOfContainers) {
        this.pierId = IdGenerator.generateId();
        this.capacity = capacity;
        this.amountOfContainers = amountOfContainers;
    }

    public long getPierId() {
        return pierId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAmountOfContainers() {
        return amountOfContainers;
    }

    public void setAmountOfContainers(int amountOfContainers) {
        this.amountOfContainers = amountOfContainers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pier pier = (Pier) o;
        if (pierId != pier.pierId) {
            return false;
        }
        if (capacity != pier.capacity) {
            return false;
        }
        return amountOfContainers == pier.amountOfContainers;
    }

    @Override
    public int hashCode() {
        int result = (int) (pierId ^ (pierId >>> 32));
        result = 31 * result + capacity;
        result = 31 * result + amountOfContainers;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pier{");
        sb.append("pierId=").append(pierId);
        sb.append(", capacity=").append(capacity);
        sb.append(", amountOfContainers=").append(amountOfContainers);
        sb.append('}');
        return sb.toString();
    }
}
