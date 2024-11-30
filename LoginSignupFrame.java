package com.greenguards;

import javax.swing.*;
import com.greenguards.backend.UserDAO;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LoginSignupFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField usernameField; // For Signup
    private JCheckBox adminCheckbox; // Admin Login Checkbox
    private JLabel toggleLabel;
    private boolean isLoginMode = true;

    public LoginSignupFrame() {
        setTitle("Green Guards - Login/Signup");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(34, 139, 34)); // Green background

        // Add Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setIcon(resizeImageIcon("C:/Users/Admin/Desktop/Study Materials/Practical/Projects/Green Guards/GGLOGO (2).png", 120, 60));
        headerPanel.add(logoLabel, BorderLayout.NORTH);

        // Welcome Text
        JLabel headerText = new JLabel("Welcome to Green Guards!", JLabel.CENTER);
        headerText.setFont(new Font("Arial", Font.BOLD, 18));
        headerText.setForeground(Color.WHITE);
        headerPanel.add(headerText, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Admin Checkbox
        adminCheckbox = new JCheckBox("Admin Login");
        adminCheckbox.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Move to bottom-right
        formPanel.add(adminCheckbox, gbc);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);

        // Toggle Label (Login/Signup)
        toggleLabel = new JLabel("<html><u>New to Green Guards? Create Account</u></html>");
        toggleLabel.setForeground(Color.BLUE);
        toggleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER; // Center the label
        formPanel.add(toggleLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(34, 139, 34)); // Green background
        footer.setPreferredSize(new Dimension(500, 40));
        JLabel footerText = new JLabel("© 2024 Green Guards. All Rights Reserved.", JLabel.CENTER);
        footerText.setForeground(Color.WHITE);
        footer.add(footerText);
        add(footer, BorderLayout.SOUTH);

        // Add Action Listeners
        submitButton.addActionListener(e -> handleSubmit());
        toggleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                toggleFormMode();
            }
        });
    }

    // Handles Submit Action
    private void handleSubmit() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        UserDAO userDAO = new UserDAO();
        if (isLoginMode) {
            if (adminCheckbox.isSelected()) {
                // Admin login logic
                boolean isAdmin = userDAO.adminLogin(email, password);
                if (isAdmin) {
                    JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                    openAdminPage();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Admin Credentials!");
                }
            } else {
                // User login logic
                boolean isLoggedIn = userDAO.login(email, password);
                if (isLoggedIn) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    openHomePage();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Credentials!");
                }
            }
        } else {
            // Signup logic
            String username = usernameField.getText();
            if (userDAO.signup(username, email, password)) {
                JOptionPane.showMessageDialog(this, "Signup Successful!");
                toggleFormMode(); // Switch back to login mode
            } else {
                JOptionPane.showMessageDialog(this, "Signup Failed!");
            }
        }
    }

    // Switch between Login and Signup Modes
    private void toggleFormMode() {
        isLoginMode = !isLoginMode;

        if (isLoginMode) {
            toggleLabel.setText("<html><u>New to Green Guards? Create Account</u></html>");
            adminCheckbox.setVisible(true); // Show admin checkbox
            removeSignupField();
        } else {
            toggleLabel.setText("<html><u>Already have an account? Login</u></html>");
            adminCheckbox.setVisible(false); // Hide admin checkbox
            addSignupField();
        }
    }

    // Adds Username Field for Signup
    private void addSignupField() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        usernameField = new JTextField(20);
        JPanel formPanel = (JPanel) getContentPane().getComponent(1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);
        revalidate();
        repaint();
    }

    // Removes Username Field when switching back to Login
    private void removeSignupField() {
        JPanel formPanel = (JPanel) getContentPane().getComponent(1);
        formPanel.remove(usernameField);
        revalidate();
        repaint();
    }

    // Opens Home Page
    private void openHomePage() {
        dispose();
        HomePageFrame homePage = new HomePageFrame();
        homePage.setVisible(true);
    }

    // Opens Admin Page
    private void openAdminPage() {
        dispose();
        JOptionPane.showMessageDialog(this, "Admin Page Coming Soon!");
    }

    // Resizes the image icon for the logo
    private ImageIcon resizeImageIcon(String path, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
