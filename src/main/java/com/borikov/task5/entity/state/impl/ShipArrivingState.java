package com.borikov.task5.entity.state.impl;

import com.borikov.task5.entity.Pier;
import com.borikov.task5.entity.Seaport;
import com.borikov.task5.entity.Ship;
import com.borikov.task5.entity.state.ShipState;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ShipArrivingState implements ShipState {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        Seaport seaport = Seaport.getInstance();
        Pier pier = seaport.getPier();
        ship.setPier(Optional.of(pier));
        switch (ship.getShipAppointment()) {
            case LOADING -> {
                ship.setShipState(new ShipLoadingState());
                LOGGER.log(Level.INFO, "Ship № {} arrived to seaport and direct to " +
                        "port № {} for loading", ship.getShipId(), pier.getPierId());
            }
            case UNLOADING -> {
                ship.setShipState(new ShipUnloadingState());
                LOGGER.log(Level.INFO, "Ship № {} arrived to seaport and direct " +
                        "to port № {} for unloading", ship.getShipId(), pier.getPierId());
            }
            case UNLOADING_LOADING -> {
                ship.setShipState(new ShipUnloadingState());
                LOGGER.log(Level.INFO, "Ship № {} arrived to seaport and direct " +
                                "to port № {} for unloading and loading",
                        ship.getShipId(), pier.getPierId());
            }
        }
    }
}
