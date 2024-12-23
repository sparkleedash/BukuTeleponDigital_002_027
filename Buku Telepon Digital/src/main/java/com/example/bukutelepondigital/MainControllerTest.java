package com.example.bukutelepondigital;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class MainControllerTest {

    private MainController controller;
    private ObservableList<Contact> contacts;
    private ListView<Contact> contactListView;

    @BeforeEach
    public void setUp() {
        // Setup objek controller dan ListView
        controller = new MainController();
        contactListView = new ListView<>();
        controller.contactListView = contactListView;

        // Menyiapkan data kontak
        contacts = FXCollections.observableArrayList(
                new Contact(1, "John Doe", "path/to/photo1", Arrays.asList("123456789")),
                new Contact(2, "Jane Smith", "path/to/photo2", Arrays.asList("987654321")),
                new Contact(3, "Alice Johnson", "path/to/photo3", Arrays.asList("555555555"))
        );

        // Mengisi kontak di dalam ListView
        contactListView.setItems(contacts);
    }

    @Test
    public void testFilterContactsWithMatchingQuery() {
        // Menyaring berdasarkan nama "John"
        controller.filterContacts("John");

        // Pastikan hanya kontak yang mengandung kata "John" yang tersisa
        assertEquals(1, contactListView.getItems().size());
        assertTrue(contactListView.getItems().get(0).getName().contains("John"));
    }

    @Test
    public void testFilterContactsWithNoMatchingQuery() {
        // Menyaring berdasarkan nama yang tidak ada "xyz"
        controller.filterContacts("xyz");

        // Pastikan tidak ada kontak yang tersisa
        assertEquals(0, contactListView.getItems().size());
    }

    @Test
    public void testFilterContactsWithEmptyQuery() {
        // Menyaring berdasarkan query kosong
        controller.filterContacts("");

        // Pastikan semua kontak muncul kembali
        assertEquals(3, contactListView.getItems().size());
    }

    @Test
    public void testLoadContacts() {
        // Menggunakan data yang sudah diinisialisasi sebelumnya di setUp
        controller.loadContacts();

        // Pastikan daftar kontak terisi
        assertEquals(3, contactListView.getItems().size());
    }
}
