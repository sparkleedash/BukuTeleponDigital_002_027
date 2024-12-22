package com.example.bukutelepondigital;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class EditContactController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button saveButton;

    private ContactDAO contactDAO = new ContactDAO();
    private Contact contactToEdit;

    // Setter untuk kontak yang akan diedit
    public void setContact(Contact contact) {
        this.contactToEdit = contact;
        nameField.setText(contact.getName());
        phoneField.setText(String.join(",", contact.getPhoneNumbers()));  // Menampilkan nomor telepon
    }

    // Menyimpan perubahan kontak
    @FXML
    public void saveChanges() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "All fields must be filled", "Please enter both name and phone number.");
            return;
        }

        // Memperbarui objek Contact
        List<String> phoneNumbers = List.of(phone.split(","));  // Jika ada beberapa nomor telepon
        contactToEdit.setName(name);
        contactToEdit.setPhoneNumbers(phoneNumbers);

        try {
            contactDAO.updateContact(contactToEdit);  // Memperbarui data di database
            showAlert("Success", "Contact updated", "The contact has been successfully updated.");
        } catch (SQLException e) {
            showAlert("Error", "Error updating contact", e.getMessage());
        }
    }

    // Menampilkan alert jika terjadi error
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

