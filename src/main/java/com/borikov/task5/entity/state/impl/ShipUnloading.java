package com.borikov.task5.entity.state.impl;

import com.borikov.task5.entity.Seaport;
import com.borikov.task5.entity.Ship;
import com.borikov.task5.entity.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShipUnloading implements ShipState {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        Seaport seaport = Seaport.getInstance();
        seaport.addContainers(ship.getFullness());
        ship.setFullness(0);
        LOGGER.log(Level.INFO, "Ship {} was unloaded", ship.getShipId());
        ship.setShipState(new ShipDeparting());
    }
}
