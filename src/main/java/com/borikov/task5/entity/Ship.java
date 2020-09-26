package com.borikov.task5.entity;

import com.borikov.task5.entity.state.ShipState;
import com.borikov.task5.entity.state.impl.ShipArriving;
import com.borikov.task5.util.IdGenerator;

public class Ship implements Runnable {
    private final long shipId;
    private final int capacity;
    private int fullness;
    private ShipAppointment shipAppointment;
    private ShipState shipState;
    private Pier pier;

    public Ship(int capacity, int fullness, ShipAppointment shipAppointment) {
        shipId = IdGenerator.generateId();
        this.capacity = capacity;
        this.fullness = fullness;
        this.shipAppointment = shipAppointment;
        shipState = new ShipArriving();
    }

    public Pier getPier() {
        return pier;
    }

    public void setPier(Pier pier) {
        this.pier = pier;
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

    public void setFullness(int fullness) {
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

    @Override
    public void run() {
        shipState.doAction(this);
        shipState.doAction(this);
        shipState.doAction(this);
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
