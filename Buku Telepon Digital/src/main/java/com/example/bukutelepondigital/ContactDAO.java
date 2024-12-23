package com.example.bukutelepondigital;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    public void addContact(Contact contact) throws SQLException {
        String query = "INSERT INTO contacts (name, photo, phone_numbers) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhotoPath());
            pstmt.setString(3, String.join(",", contact.getPhoneNumbers()));  // Menggabungkan nomor telepon
            pstmt.executeUpdate();
        }
    }

    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts ORDER BY name ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                List<String> phoneNumbers = List.of(rs.getString("phone_numbers").split(","));
                contacts.add(new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("photo"),
                        phoneNumbers
                ));
            }
        }
        return contacts;
    }

    public void updateContact(Contact contact) throws SQLException {
        String query = "UPDATE contacts SET name = ?, photo = ?, phone_numbers = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getPhotoPath());
            pstmt.setString(3, String.join(",", contact.getPhoneNumbers())); // Menggabungkan nomor telepon
            pstmt.setInt(4, contact.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Tidak ada data yang diperbarui.");
            } else {
                System.out.println("Data berhasil diperbarui.");
            }
        } catch (SQLException e) {
            System.out.println("Error saat mengupdate data kontak: " + e.getMessage());
            throw e;
        }
    }


    public void deleteContact(int id) throws SQLException {
        String query = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Contact> searchContactsByName(String name) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE name LIKE ? ORDER BY name ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%"); // Menambahkan wildcard untuk pencarian
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                List<String> phoneNumbers = List.of(rs.getString("phone_numbers").split(","));
                contacts.add(new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("photo"),
                        phoneNumbers
                ));
            }
        }
        return contacts;
    }

}

