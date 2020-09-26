package com.borikov.task5.entity.state.impl;

import com.borikov.task5.entity.Pier;
import com.borikov.task5.entity.Seaport;
import com.borikov.task5.entity.Ship;
import com.borikov.task5.entity.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShipArriving implements ShipState {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        Seaport seaport = Seaport.getInstance();
        Pier pier = seaport.getPier();
        ship.setPier(pier);
        switch (ship.getShipAppointment()) {
            case LOADING: {
                ship.setShipState(new ShipLoading());
                LOGGER.log(Level.INFO, "Ship {} arrived to seaport and direct to port {} for loading",
                        ship.getShipId(), pier.getPierId());
                break;
            }
            case UNLOADING: {
                ship.setShipState(new ShipUnloading());
                LOGGER.log(Level.INFO, "Ship {} arrived to seaport and direct to port {} for unloading",
                        ship.getShipId(), pier.getPierId());
                break;
            }
        }
    }
}
