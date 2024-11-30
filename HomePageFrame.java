package com.greenguards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.greenguards.backend.PostDAO;
import com.greenguards.components.Post;
import java.util.List;

public class HomePageFrame extends JFrame {
    private JPanel feedPanel;
    private int userId; // To store the user ID
    
    public HomePageFrame() throws HeadlessException {
		super();
		// TODO Auto-generated constructor stub
	}

	public HomePageFrame(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public HomePageFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

	public HomePageFrame(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	// Constructor to accept userId
    public HomePageFrame(int userId) {
        this.userId = userId; // Assign userId
        setTitle("Green Guards - Home");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new BorderLayout());

        // Navbar (Instagram-like)
        JPanel navbar = new JPanel();
        navbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        navbar.setBackground(new Color(34, 139, 34)); // Green color (Forest Green)
        navbar.setPreferredSize(new Dimension(1200, 90)); // Increased height

        // Add logo to the navbar
        JLabel logoLabel = new JLabel(new ImageIcon("C:/Users/Admin/Desktop/Study Materials/Practical/Projects/Green Guards/GGLOGO (2).png"));
        logoLabel.setPreferredSize(new Dimension(100, 50)); // Adjust logo size
        navbar.add(logoLabel);

        // Add buttons to the navbar
        JButton homeButton = new JButton("🏠 Home");
        JButton searchButton = new JButton("🔍 Search");
        JButton createPostButton = new JButton("➕ Create Post");
        JButton profileButton = new JButton("👤 Profile");

        navbar.add(homeButton);
        navbar.add(searchButton);
        navbar.add(createPostButton);
        navbar.add(profileButton);

        add(navbar, BorderLayout.NORTH);

        // Add action to the Create Post button
        createPostButton.addActionListener(e -> {
            CreatePostModal createPostModal = new CreatePostModal(this);
            createPostModal.setVisible(true);
        });

        // Add action to the Profile button
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Profile Page when the button is clicked, pass userId
                new ProfilePageFrame(userId).setVisible(true);
                dispose(); // Close the current home page window
            }
        });

        // Main Content Area with GridBagLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout()); // Center content
        mainPanel.setBackground(new Color(250, 240, 230));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.insets = new Insets(20, 20, 20, 20); // Spacing between panels
        gbc.anchor = GridBagConstraints.CENTER;

        // Waste Materials Section
        JPanel wastePanel = createFeedSection(
            "Waste Materials",
            "In India, 758,000 tons of waste are generated every day, with 50% disposed of in landfills. This harms nature significantly.",
            "C:/Users/Admin/Desktop/Study Materials/Practical/Projects/Green Guards/GGwaste.jpg",
            "Explore Waste Solutions",
            "Waste"
        );
        mainPanel.add(wastePanel, gbc);

        // Unique Plants Section
        gbc.gridx++; // Move to the next column
        JPanel plantsPanel = createFeedSection(
            "Unique Plants",
            "In India, approximately 6,944,608 trees were cut down over three years (2016-19). Let's adopt a plant and make a difference.",
            "C:/Users/Admin/Desktop/Study Materials/Practical/Projects/Green Guards/GGplants.png",
            "Adopt a Plant",
            "Plants"
        );
        mainPanel.add(plantsPanel, gbc);

        // Stray Animals Section
        gbc.gridx++; // Move to the next column
        JPanel animalsPanel = createFeedSection(
            "Stray Animals",
            "More than 35 million stray animals die annually due to lack of shelter. Help them find safe homes!",
            "C:/Users/Admin/Desktop/Study Materials/Practical/Projects/Green Guards/GGanimals.jpg",
            "Help Animals", 
            "Animals"
        );
        mainPanel.add(animalsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Feed Panel for displaying posts based on selected category
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS)); // Stack posts vertically
        feedPanel.setBackground(Color.WHITE);

        JScrollPane feedScroll = new JScrollPane(feedPanel);
        feedScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // Always show scroll bar
        feedScroll.setPreferredSize(new Dimension(1150, 300)); // Adjust the scroll pane height
        add(feedScroll, BorderLayout.SOUTH);

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(new Color(34, 139, 34)); // Green color (Forest Green)
        footer.setPreferredSize(new Dimension(1200, 75)); // Increased height
        footer.add(new JLabel("© 2024 Green Guards. All Rights Reserved.", JLabel.CENTER));
        footer.setForeground(Color.WHITE); // Text color for better visibility
        add(footer, BorderLayout.SOUTH);
    }

    // Helper method to create individual feed sections with a button to open the respective feed
    private JPanel createFeedSection(String title, String description, String imagePath, String buttonText, String category) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        sectionPanel.setPreferredSize(new Dimension(330, 300)); // Adjust dimensions for reduced height

        // Image
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setPreferredSize(new Dimension(350, 150)); // Adjust the image size
        sectionPanel.add(imageLabel, BorderLayout.CENTER);

        // Text Content
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("<html><h3>" + title + "</h3></html>");
        JLabel descriptionLabel = new JLabel("<html><p style='font-size:10px;'>" + description + "</p></html>");
        JButton actionButton = new JButton(buttonText);

        // Action listener to display posts for the selected category
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCategoryPosts(category);
            }
        });

        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing
        textPanel.add(actionButton);
        sectionPanel.add(textPanel, BorderLayout.SOUTH);

        return sectionPanel;
    }

    // Method to fetch and display posts based on the selected category
    private void displayCategoryPosts(String category) {
        feedPanel.removeAll(); // Clear the current feed

        // Fetch posts for the selected category
        PostDAO postDAO = new PostDAO();
        List<Post> posts = postDAO.getPostsByCategory(category);

        // Display the posts
        for (Post post : posts) {
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
            postPanel.setBackground(Color.WHITE);
            postPanel.setBorder(BorderFactory.createLineBorder(new Color(129, 199, 132), 2));

            // Add description
            JLabel descriptionLabel = new JLabel("Description: " + post.getDescription());
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            descriptionLabel.setForeground(new Color(27, 94, 32));

            // Add quantity
            JLabel quantityLabel = new JLabel("Quantity: " + post.getQuantity());
            quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            quantityLabel.setForeground(new Color(27, 94, 32));

            postPanel.add(descriptionLabel);
            postPanel.add(quantityLabel);

            feedPanel.add(postPanel);
        }

        // Refresh the feed display
        feedPanel.revalidate();
        feedPanel.repaint();
    }
}
