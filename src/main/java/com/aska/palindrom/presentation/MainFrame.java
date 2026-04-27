package com.aska.palindrom.presentation;

import static com.aska.palindrom.presentation.logging.AppLogger.LOGGER;

import com.aska.palindrom.presentation.config.WindowSettings;
import com.aska.palindrom.presentation.panel.MainPanel;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

    public MainFrame() {
        LOGGER.info("Start window program");
        WindowSettings windowSettings = WindowSettings.defaultSettings();
        configureWindow(windowSettings);
        setContentPane(new MainPanel());
    }

    private void configureWindow(WindowSettings settings) {
        setTitle(settings.appTitle());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(settings.minWindowWidth(), settings.minWindowHeight()));
        setSize(settings.windowWidth(), settings.windowHeight());

        if (settings.centerOnScreen()) {
            setLocationRelativeTo(null);
        }

        URL iconUrl = MainFrame.class.getResource(settings.iconPath());
        if (iconUrl != null) {
            setIconImage(new ImageIcon(iconUrl).getImage());
        } else {
            LOGGER.warning("Application icon not found: " + settings.iconPath());
        }
    }
}
