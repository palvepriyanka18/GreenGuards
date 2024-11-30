package com.greenguards.backend;

import java.io.File;
import java.nio.file.Files;
import java.sql.*;

import javax.swing.SwingUtilities;

import com.greenguards.SetProfilePage;
import com.greenguards.components.UserProfile;
import com.greenguards.database.DatabaseConnection;

public class UserDAO {
	public boolean login(String email, String password) {
	    String query = "SELECT * FROM users WHERE email = ? AND password = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, email);
	        stmt.setString(2, password);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            int userId = rs.getInt("id");
	            boolean isFirstLogin = rs.getBoolean("isFirstLogin");

	            if (isFirstLogin) {
	                // Redirect to profile setup if it's the first login
	                SwingUtilities.invokeLater(() -> new SetProfilePage(userId).setVisible(true));
	                return false; // Don't proceed to homepage yet
	            } else {
	                // Proceed to homepage for subsequent logins
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


    public boolean signup(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();

            // Get the generated user ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);

                // After signup is successful, navigate to SetProfilePage
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new SetProfilePage(userId).setVisible(true);
                    }
                });

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Admin login logic
    public boolean adminLogin(String email, String password) {
        String query = "SELECT * FROM admins WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch user profile by username (after login)
    public UserProfile getUserProfile(String username) {
        String query = "SELECT users.id, users.username, users.email, profiles.name, profiles.description, profiles.profile_image " +
                       "FROM users JOIN profiles ON users.id = profiles.user_id WHERE users.username = ?";
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

                // Create a UserProfile object
                userProfile = new UserProfile(userId, username, email, name, description, profileImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfile;
    }

    // Check if the username is unique during signup
    public boolean isUsernameUnique(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Username is unique if count is 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void setFirstLogin(int userId) {
        String query = "UPDATE users SET isFirstLogin = FALSE WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Set the user ID in the prepared statement to update the correct user record
            stmt.setInt(1, userId);
            
            // Execute the update query
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();  // Log any errors that occur during the update process
        }
    }


    // Update user profile with a new image (optional)
    public boolean updateProfile(int userId, String username, String name, String description, File profilePicture) {
        String query = "UPDATE profiles SET username = ?, name = ?, description = ?, profile_image = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, name);
            stmt.setString(3, description);

            // Convert profile picture to byte array
            if (profilePicture != null) {
                stmt.setBytes(4, Files.readAllBytes(profilePicture.toPath()));
            } else {
                stmt.setNull(4, java.sql.Types.BLOB); // Set NULL if no image is provided
            }

            stmt.setInt(5, userId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
