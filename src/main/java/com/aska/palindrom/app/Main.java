package com.aska.palindrom.app;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

import com.aska.palindrom.presentation.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        LOGGER.info("Start program");
        SwingUtilities.invokeLater(
                () -> {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                });
    }
}
