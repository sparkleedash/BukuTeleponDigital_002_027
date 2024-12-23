package com.example.bukutelepondigital;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ViewContactController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label photoPathLabel; // Label untuk menampilkan path foto
    @FXML
    private ImageView photoImageView; // ImageView untuk menampilkan foto

    private Contact contact; // Menyimpan kontak yang dipilih

    // Set kontak yang dipilih untuk ditampilkan
    public void setContact(Contact contact) {
        this.contact = contact;
        displayContactDetails();
    }

    // Menampilkan detail kontak
    private void displayContactDetails() {
        if (contact != null) {
            nameLabel.setText(contact.getName());
            phoneLabel.setText(String.join(", ", contact.getPhoneNumbers()));
            photoPathLabel.setText(contact.getPhotoPath()); // Menampilkan path foto

            // Menampilkan gambar
            File file = new File(contact.getPhotoPath());
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                photoImageView.setImage(image); // Mengatur gambar di ImageView
            } else {
                photoImageView.setImage(null); // Jika file tidak ada, kosongkan gambar
            }
        }
    }

    // Metode untuk kembali ke MainView (misalnya tombol Back)
    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Tutup current stage (ViewContact)
            Stage currentStage = (Stage) nameLabel.getScene().getWindow();
            currentStage.close();

            // Muat tampilan MainView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();

            // Tampilkan MainView dalam stage baru
            Stage stage = new Stage();
            stage.setTitle("Phonebook Digital");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Gagal membuka kembali MainView.");
        }
    }

    // Metode untuk membuka form EditContact
    @FXML
    private void editContact(ActionEvent event) {
        try {
            // Memuat tampilan EditContact.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditContact.fxml"));
            Parent root = loader.load();

            // Mendapatkan controller untuk EditContact
            EditContactController controller = loader.getController();

            // Mengirimkan data kontak yang akan diedit ke controller EditContact
            controller.setContact(contact);

            // Menampilkan stage untuk EditContact
            Stage stage = new Stage();
            stage.setTitle("Edit Contact");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Tunggu sampai form EditContact ditutup

            // Setelah edit selesai, perbarui tampilan di ViewContact
            displayContactDetails();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
