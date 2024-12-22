package com.example.bukutelepondigital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Memuat file FXML untuk MainView
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        AnchorPane root = loader.load();

        // Mendapatkan controller dari MainView
        MainController controller = loader.getController();

        // Set Scene dan Tampilkan
        Scene scene = new Scene(root);
        primaryStage.setTitle("Phonebook Digital");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Memuat kontak pertama kali ketika aplikasi dijalankan
        controller.loadContacts();
    }

    public static void main(String[] args) {
        launch(args);  // Memulai aplikasi JavaFX
    }
}

