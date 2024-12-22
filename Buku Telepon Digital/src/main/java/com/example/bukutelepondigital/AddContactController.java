package com.example.bukutelepondigital;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddContactController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button saveButton;

    private ContactDAO contactDAO = new ContactDAO();

    // Menyimpan kontak baru ke dalam database
    @FXML
    public void saveContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "All fields must be filled", "Please enter both name and phone number.");
            return;
        }

        // Membuat objek Contact baru
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phone);  // Bisa menambahkan logika untuk memasukkan beberapa nomor

        Contact contact = new Contact(0, name, "", phoneNumbers);  // Foto bisa diset nanti
        try {
            contactDAO.addContact(contact);  // Menyimpan ke database
            showAlert("Success", "Contact added", "The contact has been successfully added.");
        } catch (SQLException e) {
            showAlert("Error", "Error adding contact", e.getMessage());
        }

        // Setelah berhasil menambahkan, bisa kembali ke MainView atau mengosongkan form
        nameField.clear();
        phoneField.clear();
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
