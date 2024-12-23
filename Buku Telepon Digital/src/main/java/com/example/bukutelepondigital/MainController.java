package com.example.bukutelepondigital;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MainController {

    @FXML
    private TextField searchField;

    @FXML
    public ListView<Contact> contactListView;

    @FXML
    private Button addButton;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private ContactDAO contactDAO = new ContactDAO();

    public void initialize() {
        loadContacts();

        // Menambahkan stylesheet ke scene
        Scene scene = contactListView.getScene();
        if (scene != null) {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        }

        // Menetapkan cellFactory untuk ListView
        contactListView.setCellFactory(listView -> new ListCell<>() {
            private final ImageView photoView = new ImageView();
            private final Circle frame = new Circle(30); // Radius bingkai
            private final StackPane photoContainer = new StackPane();
            private final Text nameText = new Text();
            private final HBox cellLayout = new HBox(photoContainer, nameText);

            {
                // Konfigurasi foto
                photoView.setFitHeight(50);
                photoView.setFitWidth(50);
                photoView.setPreserveRatio(true);

                // Menambahkan kliping lingkaran ke gambar
                Circle clipCircle = new Circle(25);
                clipCircle.setCenterX(25);
                clipCircle.setCenterY(25);
                photoView.setClip(clipCircle);

                // Konfigurasi bingkai lingkaran
                frame.setStroke(Color.DEEPPINK); // Warna bingkai
                frame.setFill(Color.TRANSPARENT); // Latar bingkai transparan
                frame.setStrokeWidth(2);

                // Menggabungkan bingkai dan gambar dalam StackPane
                photoContainer.getChildren().addAll(frame, photoView);
                photoContainer.setAlignment(javafx.geometry.Pos.CENTER);

                // Menambahkan gaya teks
                nameText.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
                cellLayout.setSpacing(10);
                HBox.setHgrow(nameText, Priority.ALWAYS);

                // Menambahkan border default dan kelas CSS
                setStyle("-fx-border-width: 1px; -fx-border-color: transparent;");
                getStyleClass().add("list-cell"); // Menambahkan kelas CSS "list-cell" pada ListCell
            }

            @Override
            protected void updateItem(Contact contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Set foto kontak jika ada
                    if (contact.getPhotoPath() != null) {
                        File photoFile = new File(contact.getPhotoPath());
                        if (photoFile.exists()) {
                            photoView.setImage(new Image(photoFile.toURI().toString()));
                        } else {
                            photoView.setImage(null); // Foto default jika tidak ada
                        }
                    } else {
                        photoView.setImage(null); // Foto default jika null
                    }

                    nameText.setText(contact.getName());
                    setGraphic(cellLayout);

                    // Mengubah warna border saat item dihover
                    setOnMouseEntered(event -> setStyle("-fx-border-width: 1px; -fx-border-color: #ee59a6;")); // Pink saat hover
                    setOnMouseExited(event -> setStyle("-fx-border-width: 1px; -fx-border-color: transparent;")); // Reset ke transparan saat keluar dari hover

                    // Mengubah warna border saat item dipilih
                    selectedProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            setStyle("-fx-border-width: 1px; -fx-border-color: #FF5733;"); // Merah saat dipilih
                        } else {
                            setStyle("-fx-border-width: 1px; -fx-border-color: transparent;");
                        }
                    });
                }
            }
        });

        // Menambahkan listener untuk kolom pencarian
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterContacts(newValue));

        // Menambahkan double-click untuk menampilkan detail kontak
        contactListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                showContactDetails();
            }
        });
    }

    // Memuat data kontak dari database
    public void loadContacts() {
        try {
            contacts.clear();
            contacts.addAll(contactDAO.getAllContacts());
            contactListView.setItems(contacts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menyaring kontak berdasarkan query pencarian
    public void filterContacts(String query) {
        if (query == null || query.isEmpty()) {
            contactListView.setItems(contacts);
            return;
        }

        ObservableList<Contact> filteredContacts = FXCollections.observableArrayList();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredContacts.add(contact);
            }
        }
        contactListView.setItems(filteredContacts);
    }

    // Menangani aksi ketika tombol "Add Contact" ditekan
    @FXML
    public void openAddContact(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddContact.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Contact");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadContacts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Menampilkan detail kontak yang dipilih
    private void showContactDetails() {
        Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();

        if (selectedContact == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewContact.fxml"));
            Parent root = loader.load();

            ViewContactController controller = loader.getController();
            controller.setContact(selectedContact);

            Stage stage = new Stage();
            stage.setTitle("Contact Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
