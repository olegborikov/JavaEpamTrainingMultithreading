package com.borikov.task5.entity.state.impl;

import com.borikov.task5.entity.Pier;
import com.borikov.task5.entity.Seaport;
import com.borikov.task5.entity.Ship;
import com.borikov.task5.entity.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ShipDepartingState implements ShipState {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        Seaport seaport = Seaport.getInstance();
        Optional<Pier> pier = ship.getPier();
        if (pier.isPresent()) {
            seaport.releasePier(pier.get());
            LOGGER.log(Level.INFO, "Ship № {} depart from seaport", ship.getShipId());
            ship.setPier(Optional.empty());
        }
        ship.setShipState(new ShipSailingState());
    }
}
