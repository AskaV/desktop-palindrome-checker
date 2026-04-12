package com.aska.palindrom.presentation.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.*;

public final class AppLogger {
    public static final Logger LOGGER = Logger.getLogger("PalindromeApp");

    static {
        try {
            Files.createDirectories(Path.of("logs"));

            LOGGER.setUseParentHandlers(false);
            LOGGER.setLevel(Level.ALL);

            FileHandler fileHandler = new FileHandler("logs/app.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(new SimpleFormatter());

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fileHandler);
            LOGGER.addHandler(consoleHandler);
        } catch (IOException e) {
            System.err.println("Logger initialization failed: " + e.getMessage());
        }
    }

    private AppLogger() {}
}
