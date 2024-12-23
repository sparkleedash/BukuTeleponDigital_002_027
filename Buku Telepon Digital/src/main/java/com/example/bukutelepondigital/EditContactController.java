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
        try {
            // Ambil data yang diubah dari field
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String photoPath = photoPathField.getText().trim();

            // Validasi input
            validateName(name);
            validatePhoneNumber(phone);

            if (photoPath.isEmpty()) {
                throw new IllegalArgumentException("Path foto tidak boleh kosong!");
            }

            // Pisahkan nomor telepon jika ada lebih dari satu
            List<String> phoneNumbers = Arrays.asList(phone.split(",\\s*")); // Regex untuk menangani spasi setelah koma

            // Update objek kontak dengan data baru
            contact.setName(name);
            contact.setPhoneNumbers(phoneNumbers);
            contact.setPhotoPath(photoPath);

            // Perbarui data di database
            ContactDAO contactDAO = new ContactDAO();
            contactDAO.updateContact(contact);
            System.out.println("Kontak berhasil diperbarui: " + contact);

            // Tutup jendela EditContact dan buka ulang MainView
            reloadMainView();
        } catch (IllegalArgumentException e) {
            showAlert("Input Tidak Valid", e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Gagal memperbarui kontak.");
        }
    }

    // Tombol untuk menghapus kontak
    @FXML
    private void deleteContact(ActionEvent event) {
        try {
            // Tampilkan dialog konfirmasi
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus Kontak");
            alert.setHeaderText("Anda akan menghapus kontak ini:");
            alert.setContentText("Nama: " + contact.getName() + "\nApakah Anda yakin?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Hapus kontak dari database
                ContactDAO contactDAO = new ContactDAO();
                contactDAO.deleteContact(contact.getId());
                System.out.println("Kontak berhasil dihapus: " + contact);

                // Tutup jendela EditContact dan buka ulang MainView
                reloadMainView();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Gagal menghapus kontak.");
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
            showAlert("Kesalahan", "Gagal membuka kembali MainView.");
        }
    }

    // Validasi nama
    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        if (name.length() > 10) {
            throw new IllegalArgumentException("Nama tidak boleh lebih dari 10 karakter.");
        }
        if (!name.matches("[a-zA-Z-]+")) {
            throw new IllegalArgumentException("Nama hanya boleh berisi huruf (a-z, A-Z) dan tanda '-' saja.");
        }
    }

    // Validasi nomor telepon
    private void validatePhoneNumber(String phone) {
        if (phone.isEmpty()) {
            throw new IllegalArgumentException("Nomor telepon tidak boleh kosong.");
        }
        if (!phone.matches("08[0-9]{10}")) {
            throw new IllegalArgumentException("Nomor telepon harus berisi 12 digit, diawali dengan '08', dan hanya angka (0-9).");
        }
    }

    // Menampilkan alert untuk error
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
