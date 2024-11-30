package com.greenguards;

import javax.swing.*;
import com.greenguards.backend.PostDAO;
import com.greenguards.components.Post;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class CreatePostModal extends JDialog {
    private byte[] imageBytes = null; // To store the selected image as a byte array

    public CreatePostModal(JFrame parent) {
        super(parent, "Create New Post", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Description field
        formPanel.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        formPanel.add(descriptionField);

        // Quantity field
        formPanel.add(new JLabel("Quantity:"));
        JTextField quantityField = new JTextField();
        formPanel.add(quantityField);

        // Tag dropdown
        formPanel.add(new JLabel("Category:"));
        JComboBox<String> tagComboBox = new JComboBox<>(new String[]{"Waste", "Plants", "Animals"});
        formPanel.add(tagComboBox);

        // Image upload button
        formPanel.add(new JLabel("Image:"));
        JButton uploadButton = new JButton("Upload");
        formPanel.add(uploadButton);

        // Action for upload button
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try (FileInputStream fis = new FileInputStream(selectedFile)) {
                    imageBytes = fis.readAllBytes();
                    JOptionPane.showMessageDialog(this, "Image selected successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error reading image file.");
                }
            }
        });

        // Submit button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action on submit button
        submitButton.addActionListener(e -> {
            try {
                String description = descriptionField.getText();
                int quantity = Integer.parseInt(quantityField.getText()); // Parse quantity to int
                String tag = tagComboBox.getSelectedItem().toString();

                if (imageBytes == null) {
                    JOptionPane.showMessageDialog(this, "Please upload an image.");
                    return;
                }

                Post post = new Post(description, quantity, tag, imageBytes);
                PostDAO postDAO = new PostDAO();
                boolean isCreated = postDAO.createPost(post);

                if (isCreated) {
                    JOptionPane.showMessageDialog(this, "Post created successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create post.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
            }
        });
    }
}
