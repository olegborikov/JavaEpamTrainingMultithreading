package com.borikov.task5.entity.state.impl;

import com.borikov.task5.entity.Seaport;
import com.borikov.task5.entity.Ship;
import com.borikov.task5.entity.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShipLoading implements ShipState {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        Seaport seaport = Seaport.getInstance();
        seaport.loadShip(ship);
        LOGGER.log(Level.INFO, "Ship № {} was loaded", ship.getShipId());
        ship.setShipState(new ShipDeparting());
    }
}
