package com.greenguards.backend;

import com.greenguards.database.DatabaseConnection;
import com.greenguards.components.Post;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    // Fetch all posts from the database
    public List<Post> fetchAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM posts";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            // Loop through the result set and add each post to the list
            while (rs.next()) {
                posts.add(new Post(rs.getInt("id"), 
                                   rs.getString("description"),
                                   rs.getInt("quantity"),
                                   rs.getString("tag"),
                                   rs.getBytes("image")));  // Image as byte array
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return posts;
    }

    // Fetch posts by category (Waste, Plants, Animals)
    public List<Post> getPostsByCategory(String category) {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM posts WHERE tag = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            
            // Loop through the result set and add each post to the list
            while (rs.next()) {
                posts.add(new Post(rs.getInt("id"),
                                   rs.getString("description"),
                                   rs.getInt("quantity"),
                                   rs.getString("tag"),
                                   rs.getBytes("image")));  // Image as byte array
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return posts;
    }

    // Insert a new post into the database with an image as a BLOB
    public boolean createPost(Post post) {
        String query = "INSERT INTO posts (description, quantity, tag, image) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Set the values from the Post object
            stmt.setString(1, post.getDescription());
            stmt.setInt(2, post.getQuantity());
            stmt.setString(3, post.getTag());
            
            // Convert image file to byte array and set it in the PreparedStatement
            if (post.getImage() != null) {
                stmt.setBytes(4, post.getImage());  // Set the image as byte array
            } else {
                stmt.setNull(4, Types.BLOB);  // Set image as null if not provided
            }
            
            // Execute the query and check if the post was created
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
