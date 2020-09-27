package com.borikov.task5.entity.state.impl;

import com.borikov.task5.entity.Ship;
import com.borikov.task5.entity.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShipSailingState implements ShipState {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        ship.setShipState(new ShipArrivingState());
        LOGGER.log(Level.INFO, "Ship № {} is arriving to seaport", ship.getShipId());
    }
}
