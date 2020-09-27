package com.borikov.task5.reader;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomFileReader {
    private static final Logger LOGGER = LogManager.getLogger();

    public List<String> readText(String fileName) {
        Path path = Paths.get(fileName);
        try (Stream<String> linedTextStream = Files.lines(path)) {
            List<String> linedTextList = linedTextStream.collect(Collectors.toList());
            LOGGER.log(Level.INFO,
                    "There were read {} lines from file", linedTextList.size());
            return linedTextList;
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Error with file {}", fileName, e);
            throw new RuntimeException("Error with file " + fileName, e);
        }
    }
}
