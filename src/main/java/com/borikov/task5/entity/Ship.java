package com.borikov.task5.entity;

import com.borikov.task5.entity.state.ShipState;
import com.borikov.task5.entity.state.impl.ShipArriving;
import com.borikov.task5.entity.state.impl.ShipSailing;
import com.borikov.task5.util.IdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Ship implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final long shipId;
    private final int capacity;
    private AtomicInteger fullness;
    private ShipAppointment shipAppointment;
    private ShipState shipState;
    private Optional<Pier> pier;

    public Ship(int capacity, AtomicInteger fullness, ShipAppointment shipAppointment) {
        shipId = IdGenerator.generateId();
        this.capacity = capacity;
        this.fullness = fullness;
        this.shipAppointment = shipAppointment;
        shipState = new ShipArriving();
    }

    public Optional<Pier> getPier() {
        return pier;
    }

    public void setPier(Optional<Pier> pier) {
        this.pier = pier;
    }

    public long getShipId() {
        return shipId;
    }

    public int getCapacity() {
        return capacity;
    }

    public AtomicInteger getFullness() {
        return fullness;
    }

    public void setFullness(AtomicInteger fullness) {
        this.fullness = fullness;
    }

    public ShipAppointment getShipAppointment() {
        return shipAppointment;
    }

    public void setShipAppointment(ShipAppointment shipAppointment) {
        this.shipAppointment = shipAppointment;
    }

    public ShipState getShipState() {
        return shipState;
    }

    public void setShipState(ShipState shipState) {
        this.shipState = shipState;
    }

    public boolean addContainer() {
        boolean result;
        if (fullness.get() + 1 <= capacity) {
            fullness.incrementAndGet();
            LOGGER.log(Level.INFO, "Container was load to ship № {}", shipId);
            result = true;
        } else {
            LOGGER.log(Level.INFO, "Ship № {} has no free space left", shipId);
            result = false;
        }
        return result;
    }

    public boolean getContainer() {
        boolean result;
        if (fullness.get() > 0) {
            fullness.decrementAndGet();
            LOGGER.log(Level.INFO, "Container was unload from ship № {}", shipId);
            result = true;
        } else {
            LOGGER.log(Level.INFO, "Ship № {} has no containers left", shipId);
            result = false;
        }
        return result;
    }

    @Override
    public void run() {
        do {
            shipState.doAction(this);
        } while (shipState.getClass() != ShipSailing.class);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("shipId=").append(shipId);
        sb.append(", capacity=").append(capacity);
        sb.append(", fullness=").append(fullness);
        sb.append(", shipAppointment=").append(shipAppointment);
        sb.append(", shipState=").append(shipState);
        sb.append(", pier=").append(pier);
        sb.append('}');
        return sb.toString();
    }
}
