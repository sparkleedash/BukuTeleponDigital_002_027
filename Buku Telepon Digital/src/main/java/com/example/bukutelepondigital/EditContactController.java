package com.example.bukutelepondigital;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EditContactController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField photoPathField;

    private Contact contact; // Kontak yang sedang diedit

    // Set kontak yang akan diedit
    public void setContact(Contact contact) {
        this.contact = contact;
        displayContactDetails();
    }

    // Menampilkan detail kontak yang akan diedit
    private void displayContactDetails() {
        if (contact != null) {
            nameField.setText(contact.getName());
            phoneField.setText(String.join(", ", contact.getPhoneNumbers())); // Menampilkan nomor telepon
            photoPathField.setText(contact.getPhotoPath()); // Menampilkan path foto
        }
    }

    // Tombol untuk menyimpan perubahan
    @FXML
    private void saveChanges(ActionEvent event) {
        // Ambil data yang diubah dari field
        String name = nameField.getText();
        String phone = phoneField.getText();
        String photoPath = photoPathField.getText();

        // Pisahkan nomor telepon jika ada lebih dari satu
        List<String> phoneNumbers = Arrays.asList(phone.split(",\\s*")); // Menggunakan regex untuk menangani spasi setelah koma

        // Update objek kontak dengan data baru
        contact.setName(name);
        contact.setPhoneNumbers(phoneNumbers);
        contact.setPhotoPath(photoPath);

        // Perbarui data di database
        ContactDAO contactDAO = new ContactDAO();
        try {
            contactDAO.updateContact(contact);
            System.out.println("Kontak berhasil diperbarui: " + contact);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal memperbarui kontak.");
        }

        // Tutup jendela EditContact dan buka ulang MainView
        reloadMainView();
    }

    // Tombol untuk menghapus kontak
    @FXML
    private void deleteContact(ActionEvent event) {
        // Tampilkan dialog konfirmasi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus Kontak");
        alert.setHeaderText("Anda akan menghapus kontak ini:");
        alert.setContentText("Nama: " + contact.getName() + "\nApakah Anda yakin?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Hapus kontak dari database
            ContactDAO contactDAO = new ContactDAO();
            try {
                contactDAO.deleteContact(contact.getId());
                System.out.println("Kontak berhasil dihapus: " + contact);

                // Tutup jendela EditContact dan buka ulang MainView
                reloadMainView();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Gagal menghapus kontak.");
            }
        }
    }

    // Memuat ulang MainView setelah operasi selesai
    private void reloadMainView() {
        try {
            // Tutup jendela EditContact
            Stage currentStage = (Stage) nameField.getScene().getWindow();
            currentStage.close();

            // Muat kembali tampilan MainView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();

            // Tampilkan kembali MainView
            Stage stage = new Stage();
            stage.setTitle("Phonebook Digital");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Gagal membuka kembali MainView.");
        }
    }
}
