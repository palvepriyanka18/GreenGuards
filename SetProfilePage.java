package com.greenguards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.*;
import javax.swing.JTextField;

import com.greenguards.backend.UserDAO;

public class SetProfilePage extends JFrame {
    private JTextField usernameField, nameField;
    private JTextArea descriptionArea;
    private JLabel profilePictureLabel;
    private JButton uploadButton, saveButton, cancelButton;
    private File selectedProfilePicture;
    private UserDAO userDAO;
    private int userId;
    private JComboBox<String> colorSchemeComboBox;

    public SetProfilePage(int userId) {
        this.userId = userId;
        userDAO = new UserDAO();
        initialize();
    }

    private void initialize() {
        setTitle("Set Up Your Profile");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Complete Your Profile", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34)); // Green theme
        add(headerLabel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username
        centerPanel.add(new JLabel("Username (unique):"));
        usernameField = new JTextField();
        centerPanel.add(usernameField);

        // Name
        centerPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        centerPanel.add(nameField);

        // Description
        centerPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        centerPanel.add(scrollPane);

        // Profile Picture Upload
        centerPanel.add(new JLabel("Profile Picture:"));
        JPanel picturePanel = new JPanel(new BorderLayout());
        profilePictureLabel = new JLabel("No image selected", JLabel.CENTER);
        profilePictureLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        picturePanel.add(profilePictureLabel, BorderLayout.CENTER);
        uploadButton = new JButton("Upload Image");
        picturePanel.add(uploadButton, BorderLayout.SOUTH);
        centerPanel.add(picturePanel);

        // Color Scheme ComboBox
        centerPanel.add(new JLabel("Color Scheme:"));
        colorSchemeComboBox = new JComboBox<>(new String[]{"Light", "Dark", "Green", "Blue"});
        centerPanel.add(colorSchemeComboBox);

        // Action Listeners for Image Upload
        uploadButton.addActionListener(e -> uploadImage());

        add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Save Action Listener
        saveButton.addActionListener(e -> saveProfile());

        // Cancel Action Listener
        cancelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Profile setup canceled. Redirecting to home.");
            new HomePageFrame(userId).setVisible(true);
            dispose();
        });

        // Set color scheme
        centerPanel.setBackground(new Color(245, 255, 250)); // Light green by default
        buttonPanel.setBackground(new Color(245, 255, 250));

    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Picture");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedProfilePicture = fileChooser.getSelectedFile();
            profilePictureLabel.setText(selectedProfilePicture.getName());
        }
    }
    
    
    private void saveProfile() {
        String username = usernameField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        String colorScheme = (String) colorSchemeComboBox.getSelectedItem();

        // Validate fields
        if (username.isEmpty() || name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!username.matches("^[a-z0-9_]+$")) {
            JOptionPane.showMessageDialog(this, "Username must be lowercase, numbers, or underscores only.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check username uniqueness
        if (!userDAO.isUsernameUnique(username)) {
            JOptionPane.showMessageDialog(this, "Username is already taken. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save profile
        boolean success = userDAO.updateProfile(userId, username, name, description, selectedProfilePicture);
        if (success) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            userDAO.setFirstLogin(userId);  // Set first login to false
            new HomePageFrame(userId).setVisible(true); // Redirect to homepage
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
