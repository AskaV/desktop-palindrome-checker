package com.aska.palindrom.presentation.config;

import javax.swing.border.EmptyBorder;

public class UiBorders {
    private UiBorders() {}

    public static EmptyBorder padding(int value) {
        return new EmptyBorder(value, value, value, value);
    }
}
