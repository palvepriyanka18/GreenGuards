package com.greenguards.backend;

import com.greenguards.database.DatabaseConnection;
import com.greenguards.components.UserProfile;

import java.io.File;
import java.nio.file.Files;
import java.sql.*;

public class ProfileDAO {

	public UserProfile getUserProfile(String username) {
	    String query = "SELECT id, username, email, name, description, profile_image FROM users WHERE username = ?";
	    UserProfile userProfile = null;

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            int userId = rs.getInt("id");
	            String email = rs.getString("email");
	            String name = rs.getString("name");
	            String description = rs.getString("description");
	            byte[] profileImage = rs.getBytes("profile_image");

	            // Create a UserProfile object with all the fields
	            userProfile = new UserProfile(userId, username, email, name, description, profileImage);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return userProfile;
	}

	public boolean updateProfile(int userId, String username, String description, File profilePicture) {
	    String query = "UPDATE profiles SET username = ?, description = ?, profile_picture = ? WHERE user_id = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, username);
	        stmt.setString(2, description);

	        // Convert profile picture to byte array if it is not null
	        if (profilePicture != null) {
	            stmt.setBytes(3, Files.readAllBytes(profilePicture.toPath()));
	        } else {
	            stmt.setNull(3, java.sql.Types.BLOB);
	        }

	        stmt.setInt(4, userId);
	        return stmt.executeUpdate() > 0; // Return true if the update was successful
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}


    // Update user email by userId
    public void updateUserEmail(int userId, String newEmail) {
        String query = "UPDATE users SET email = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newEmail);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public UserProfile getUserProfileById(int userId) {
        UserProfile userProfile = null;
        String query = "SELECT username, email, name, description, profile_image FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String name = rs.getString("name");
                String description = rs.getString("description");
                byte[] profileImage = rs.getBytes("profile_image");

                // Create the UserProfile object with all fields
                userProfile = new UserProfile(userId, username, email, name, description, profileImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfile;
    }

    // Update user password by userId
    public void updateUserPassword(int userId, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
