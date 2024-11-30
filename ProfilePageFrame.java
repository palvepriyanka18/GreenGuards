package com.greenguards;

import javax.swing.*;
import com.greenguards.backend.ProfileDAO;
import com.greenguards.components.UserProfile;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePageFrame extends JFrame {
    
    public ProfilePageFrame(int userId) {
        setTitle("Green Guards - Profile");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fetch profile data using userId
        ProfileDAO profileDAO = new ProfileDAO();
        UserProfile userProfile = profileDAO.getUserProfileById(userId); // Ensure this method exists

        // Layout
        setLayout(new BorderLayout());

        // Navbar
        JPanel navbar = new JPanel();
        navbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        navbar.setBackground(new Color(34, 139, 34));
        navbar.setPreferredSize(new Dimension(800, 90));

        JButton homeButton = new JButton("🏠 Home");
        homeButton.addActionListener(e -> {
            new HomePageFrame().setVisible(true);
            dispose();
        });
        navbar.add(homeButton);
        add(navbar, BorderLayout.NORTH);

        // Profile Content Panel
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(new Color(250, 240, 230));

        // Profile Picture
        JLabel profilePicLabel = new JLabel(new ImageIcon(userProfile.getProfileImage()));
        profilePicLabel.setPreferredSize(new Dimension(150, 150));
        profilePanel.add(profilePicLabel);

        // Username
        JLabel userNameLabel = new JLabel("Username: " + userProfile.getUsername());
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(userNameLabel);

        // User Description
        JTextArea userDescTextArea = new JTextArea(userProfile.getDescription());
        userDescTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        userDescTextArea.setEditable(false);
        userDescTextArea.setLineWrap(true);
        userDescTextArea.setWrapStyleWord(true);
        profilePanel.add(userDescTextArea);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            new LoginSignupFrame().setVisible(true);
            dispose();
        });
        profilePanel.add(logoutButton);

        add(profilePanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(34, 139, 34));
        footer.setPreferredSize(new Dimension(800, 50));
        footer.add(new JLabel("© 2024 Green Guards. All Rights Reserved.", JLabel.CENTER));
        add(footer, BorderLayout.SOUTH);
    }
}
