package com.borikov.task5.entity;

import com.borikov.task5.entity.state.ShipState;
import com.borikov.task5.entity.state.impl.ShipSailingState;
import com.borikov.task5.util.IdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class Ship implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final long shipId;
    private final int capacity;
    private int fullness;
    private ShipAppointment shipAppointment;
    private ShipState shipState;
    private Optional<Pier> pier;

    public Ship(int capacity, int fullness, ShipAppointment shipAppointment) {
        shipId = IdGenerator.generateId();
        this.capacity = capacity;
        this.fullness = fullness;
        this.shipAppointment = shipAppointment;
        shipState = new ShipSailingState();
    }

    public long getShipId() {
        return shipId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFullness() {
        return fullness;
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

    public Optional<Pier> getPier() {
        return pier;
    }

    public void setPier(Optional<Pier> pier) {
        this.pier = pier;
    }

    public boolean isFull() {
        boolean result = fullness == capacity;
        if (result) {
            LOGGER.log(Level.INFO, "Ship № {} has no free space left", shipId);
        }
        return result;
    }

    public boolean isEmpty() {
        boolean result = fullness == 0;
        if (result) {
            LOGGER.log(Level.INFO, "Ship № {} has no containers left", shipId);
        }
        return result;
    }

    public void addContainer() {
        fullness++;
        LOGGER.log(Level.INFO, "Container was load to ship № {}", shipId);
        LOGGER.log(Level.INFO, "Ship № {} capacity: {}, fullness: {}",
                shipId, capacity, fullness);
    }

    public void deleteContainer() {
        fullness--;
        LOGGER.log(Level.INFO, "Container was unload from ship № {}", shipId);
        LOGGER.log(Level.INFO, "Ship № {} capacity: {}, fullness: {}",
                shipId, capacity, fullness);
    }

    @Override
    public void run() {
        do {
            shipState.doAction(this);
        } while (shipState.getClass() != ShipSailingState.class);
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
        if (fullness != ship.fullness) {
            return false;
        }
        if (shipAppointment != ship.shipAppointment) {
            return false;
        }
        if (shipState != null ? !shipState.equals(ship.shipState)
                : ship.shipState != null) {
            return false;
        }
        return pier != null ? pier.equals(ship.pier) : ship.pier == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (shipId ^ (shipId >>> 32));
        result = 31 * result + capacity;
        result = 31 * result + fullness;
        result = 31 * result + (shipAppointment != null ? shipAppointment.hashCode() : 0);
        result = 31 * result + (shipState != null ? shipState.hashCode() : 0);
        result = 31 * result + (pier != null ? pier.hashCode() : 0);
        return result;
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
