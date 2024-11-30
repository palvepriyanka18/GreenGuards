package com.greenguards;

import com.greenguards.backend.PostDAO;
import com.greenguards.components.Post;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FeedPageFrame extends JFrame {
    private String category;

    public FeedPageFrame(String category) {
        this.category = category;
        setTitle(category + " Feed");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 245, 245));

        // Add title label
        JLabel titleLabel = new JLabel(category + " Feed", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(27, 94, 32));

        // Add scrollable list of posts
        JScrollPane postListScroll = new JScrollPane();
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.WHITE);

        PostDAO postDAO = new PostDAO();
        List<Post> posts = postDAO.getPostsByCategory(category);

        for (Post post : posts) {
            JPanel postItemPanel = new JPanel();
            postItemPanel.setLayout(new BoxLayout(postItemPanel, BoxLayout.Y_AXIS));
            postItemPanel.setBackground(Color.WHITE);
            postItemPanel.setBorder(BorderFactory.createLineBorder(new Color(129, 199, 132), 2));

            // Add description
            JLabel descriptionLabel = new JLabel("Description: " + post.getDescription());
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            descriptionLabel.setForeground(new Color(27, 94, 32));

            // Add quantity
            JLabel quantityLabel = new JLabel("Quantity: " + post.getQuantity());
            quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            quantityLabel.setForeground(new Color(27, 94, 32));

            postItemPanel.add(descriptionLabel);
            postItemPanel.add(quantityLabel);
            postPanel.add(postItemPanel);
        }

        postListScroll.setViewportView(postPanel);

        // Add components to the frame
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(postListScroll, BorderLayout.CENTER);
    }
}
