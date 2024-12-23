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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MainController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Contact> contactListView;

    @FXML
    private Button addButton;

    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private ContactDAO contactDAO = new ContactDAO();

    public void initialize() {
        loadContacts();

        contactListView.setCellFactory(listView -> new ListCell<>() {
            private final ImageView photoView = new ImageView();
            private final Text nameText = new Text();
            private final HBox cellLayout = new HBox(photoView, nameText);

            {
                photoView.setFitHeight(50); // Ukuran tinggi gambar
                photoView.setFitWidth(50); // Ukuran lebar gambar
                photoView.setPreserveRatio(true);

                nameText.setStyle("-fx-font-size: 16px; -fx-padding: 10px;"); // Ukuran font dan padding teks

                cellLayout.setSpacing(10); // Jarak antara foto dan teks
                HBox.setHgrow(nameText, Priority.ALWAYS); // Teks menyesuaikan ruang
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
                }
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterContacts(newValue));

        contactListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                showContactDetails();
            }
        });
    }

    public void loadContacts() {
        try {
            contacts.clear();
            contacts.addAll(contactDAO.getAllContacts());
            contactListView.setItems(contacts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterContacts(String query) {
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
