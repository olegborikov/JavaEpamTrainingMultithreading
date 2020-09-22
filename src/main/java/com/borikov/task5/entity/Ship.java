package com.borikov.task5.entity;

import com.borikov.task5.util.IdGenerator;

public class Ship implements Runnable {
    private final long shipId;
    private int capacity;
    private int amountOfContainers;

    public Ship(int capacity, int amountOfContainers) {
        this.shipId = IdGenerator.generateId();
        this.capacity = capacity;
        this.amountOfContainers = amountOfContainers;
    }

    public long getShipId() {
        return shipId;
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
    public void run() {
        Seaport seaport = Seaport.getInstance();
        Pier pier = seaport.getPier();
        // TODO: 22.09.2020 exception throw if capacity dont suit?
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ship ship = (Ship) o;
        if (shipId != ship.shipId) {
            return false;
        }
        if (capacity != ship.capacity) {
            return false;
        }
        return amountOfContainers == ship.amountOfContainers;
    }

    @Override
    public int hashCode() {
        int result = (int) (shipId ^ (shipId >>> 32));
        result = 31 * result + capacity;
        result = 31 * result + amountOfContainers;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("shipId=").append(shipId);
        sb.append(", capacity=").append(capacity);
        sb.append(", amountOfContainers=").append(amountOfContainers);
        sb.append('}');
        return sb.toString();
    }
}
