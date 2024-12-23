package com.example.bukutelepondigital;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.TransferMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.Dragboard;

import java.util.Arrays;

public class AddContactController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField photoPathField; // Input untuk path foto

    private ContactDAO contactDAO = new ContactDAO(); // Akses ke database

    @FXML
    private void initialize() {
        // Menambahkan event handler drag and drop ke photoPathField
        photoPathField.setOnDragOver(this::handleDragOver);
        photoPathField.setOnDragDropped(this::handleDragDropped);
    }

    // Event handler untuk drag over
    private void handleDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    // Event handler untuk drag dropped
    private void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {
            // Ambil path dari file yang dijatuhkan
            String filePath = db.getFiles().get(0).getAbsolutePath();
            photoPathField.setText(filePath); // Set path gambar ke dalam text field
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void saveContact() {
        // Ambil nilai dari input pengguna
        String name = nameField.getText();
        String phone = phoneField.getText();
        String photoPath = photoPathField.getText();

        // Validasi input (opsional, tambahkan sesuai kebutuhan)
        if (name.isEmpty() || phone.isEmpty() || photoPath.isEmpty()) {
            System.out.println("Semua field harus diisi!");
            return;
        }

        // Pisahkan nomor telepon dengan koma jika lebih dari satu
        String[] phoneNumbers = phone.split(",");

        // Buat objek kontak baru
        Contact contact = new Contact(0, name, photoPath, Arrays.asList(phoneNumbers));

        // Simpan kontak ke database
        try {
            contactDAO.addContact(contact);
            System.out.println("Kontak berhasil disimpan: " + contact);

            // Tutup form setelah menyimpan
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal menyimpan kontak.");
        }
    }
}
