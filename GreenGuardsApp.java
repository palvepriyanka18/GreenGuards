package com.greenguards;

import javax.swing.*;

public class GreenGuardsApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Launch the login/signup frame
            LoginSignupFrame loginSignupPage = new LoginSignupFrame();
            // Set up the behavior for a successful login/signup
            loginSignupPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginSignupPage.setVisible(true);
        });
    }
}
