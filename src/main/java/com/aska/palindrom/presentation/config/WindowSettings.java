package com.aska.palindrom.presentation.config;

public record WindowSettings(
        String appTitle,
        String iconPath,
        int windowWidth,
        int windowHeight,
        int minWindowWidth,
        int minWindowHeight,
        boolean centerOnScreen) {

    public static WindowSettings defaultSettings() {
        return new WindowSettings(
                "Desktop Palindrome Checker", "/images/icon.png", 1100, 650, 900, 550, true);
    }
}
