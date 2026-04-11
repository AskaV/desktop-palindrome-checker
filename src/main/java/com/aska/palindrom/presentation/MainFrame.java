package com.aska.palindrom.presentation;

import com.aska.palindrom.presentation.config.WindowSettings;
import com.aska.palindrom.presentation.panel.MainPanel;
import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        WindowSettings windowSettings = WindowSettings.defaultSettings();
        configureWindow(windowSettings);
        setContentPane(new MainPanel());
        setVisible(true);
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
        }
    }
}
