package com.greenguards;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    public HeaderPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 139, 34)); // Green theme

        // Logo Section
        JLabel logoLabel = new JLabel("Green Guards", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        logoLabel.setForeground(Color.WHITE);

        // Login Button
        JButton loginButton = new JButton("Login / Signup");
        loginButton.setBackground(Color.WHITE);

        add(logoLabel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.EAST);
    }
}
