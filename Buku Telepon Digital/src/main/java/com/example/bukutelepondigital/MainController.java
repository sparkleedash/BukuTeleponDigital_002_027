package com.example.bukutelepondigital;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class MainController {
    @FXML
    private ListView<Contact> contactListView;
    @FXML
    private TextField searchField;
    private ContactDAO contactDAO = new ContactDAO();

    // Memuat semua kontak
    public void loadContacts() {
        try {
            List<Contact> contacts = contactDAO.getAllContacts();
            contactListView.getItems().clear();
            contactListView.getItems().addAll(contacts);
        } catch (SQLException e) {
            showAlert("Error", "Error loading contacts", e.getMessage());
        }
    }

    // Menambah kontak
    public void addContact(Contact contact) {
        try {
            contactDAO.addContact(contact);
            loadContacts();
        } catch (SQLException e) {
            showAlert("Error", "Error adding contact", e.getMessage());
        }
    }

    // Menghapus kontak
    public void deleteContact(Contact contact) {
        try {
            contactDAO.deleteContact(contact.getId());
            loadContacts();
        } catch (SQLException e) {
            showAlert("Error", "Error deleting contact", e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        // Menambahkan listener pencarian
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchContacts(newValue);
        });
    }

    // Mencari kontak berdasarkan nama
    private void searchContacts(String query) {
        try {
            List<Contact> contacts = contactDAO.searchContactsByName(query);
            contactListView.getItems().clear();
            contactListView.getItems().addAll(contacts);
        } catch (SQLException e) {
            showAlert("Error", "Error searching contacts", e.getMessage());
        }
    }

}

