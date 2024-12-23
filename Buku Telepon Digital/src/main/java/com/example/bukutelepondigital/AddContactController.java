package com.example.bukutelepondigital;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class AddContactController {

    @FXML
    private ImageView backgroundImageView; // ImageView untuk background

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

        // Menetapkan gambar latar belakang
        setBackground();

        // Menambahkan CSS ke scene
        Scene scene = nameField.getScene();
        if (scene != null) {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        }
    }

    // Metode untuk menetapkan gambar latar belakang
    private void setBackground() {
        // Path gambar yang digunakan
        String imagePath = "file:/C:/Users/Computer/Downloads/profil5.jpg"; // Gunakan format file URI
        Image image = new Image(imagePath);
        backgroundImageView.setImage(image);
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

    // Metode untuk menyimpan kontak yang baru
    @FXML
    private void saveContact() {
        try {
            // Ambil nilai dari input pengguna
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String photoPath = photoPathField.getText().trim();

            // Validasi input
            validateName(name);
            validatePhoneNumber(phone);

            if (photoPath.isEmpty()) {
                throw new IllegalArgumentException("Path foto tidak boleh kosong!");
            }

            // Pisahkan nomor telepon dengan koma jika lebih dari satu
            String[] phoneNumbers = phone.split(",");

            // Buat objek kontak baru
            Contact contact = new Contact(0, name, photoPath, Arrays.asList(phoneNumbers));

            // Simpan kontak ke database
            contactDAO.addContact(contact);
            System.out.println("Kontak berhasil disimpan: " + contact);

            // Tutup form setelah menyimpan
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (IllegalArgumentException e) {
            showAlert("Input Tidak Valid", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Terjadi kesalahan saat menyimpan kontak.");
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

    // Metode untuk kembali ke MainController
    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Tutup form AddContact
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();

            // Muat tampilan MainView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();

            // Tampilkan MainView dalam stage baru
            Stage mainStage = new Stage();
            mainStage.setTitle("Phonebook Digital");
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Gagal membuka kembali MainView.");
        }
    }
}
