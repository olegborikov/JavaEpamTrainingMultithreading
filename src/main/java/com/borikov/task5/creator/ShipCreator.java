package com.borikov.task5.creator;

import com.borikov.task5.entity.Ship;
import com.borikov.task5.entity.ShipAppointment;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShipCreator {
    private static final Logger LOGGER = LogManager.getLogger();

    public Ship createFromNumbers(List<Integer> numbers) {
        Ship ship = new Ship(numbers.get(0), new AtomicInteger(numbers.get(1)),
                ShipAppointment.values()[numbers.get(2)]);
        LOGGER.log(Level.INFO, "{} was created", ship);
        return ship;
    }
}
