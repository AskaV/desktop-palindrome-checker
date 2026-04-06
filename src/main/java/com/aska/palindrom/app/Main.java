package com.aska.palindrom.app;

import com.aska.palindrom.presentation.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                });
    }
}
