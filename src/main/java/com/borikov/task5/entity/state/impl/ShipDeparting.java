package com.borikov.task5.entity.state.impl;

import com.borikov.task5.entity.Pier;
import com.borikov.task5.entity.Seaport;
import com.borikov.task5.entity.Ship;
import com.borikov.task5.entity.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShipDeparting implements ShipState {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        Seaport seaport = Seaport.getInstance();
        Pier pier = ship.getPier();
        seaport.releasePier(pier);
        LOGGER.log(Level.INFO, "Ship {} depart from seaport", ship.getShipId());
        ship.setPier(null); // TODO: 25.09.2020 refactor
        ship.setShipState(null); // TODO: 25.09.2020 refactor
    }
}
