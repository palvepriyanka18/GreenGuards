package com.greenguards;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {
    public FooterPanel() {
        setBackground(new Color(34, 139, 34)); // Green theme

        JLabel footerLabel = new JLabel("© 2024 Green Guards. All rights reserved.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);

        add(footerLabel);
    }
}

