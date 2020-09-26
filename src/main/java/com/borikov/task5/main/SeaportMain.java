package com.borikov.task5.main;

import com.borikov.task5.creator.ShipCreator;
import com.borikov.task5.entity.Ship;
import com.borikov.task5.parser.DataParser;
import com.borikov.task5.reader.CustomFileReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeaportMain {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FILE_PATH = "input/data.txt";
    private static final String SHIP_NAME = "Ship";

    public static void main(String[] args) {
        CustomFileReader customFileReader = new CustomFileReader();
        DataParser dataParser = new DataParser();
        ShipCreator shipCreator = new ShipCreator();
        List<Ship> ships = new ArrayList<>();
        List<String> readLines = customFileReader.readText(FILE_PATH);
        for (String line : readLines) {
            List<Integer> numbers = dataParser.parseLineToNumberList(line);
            Ship ship = shipCreator.createFromNumbers(numbers);
            ships.add(ship);
        }
        for (Ship ship : ships) {
            try {
                Thread thread = new Thread(ship);
                thread.setName(SHIP_NAME + ship.getShipId());
                thread.start();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "Thread was interrupted");
            }
        }
    }
}
