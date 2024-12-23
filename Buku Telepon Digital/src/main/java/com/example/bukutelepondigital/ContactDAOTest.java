package com.example.bukutelepondigital;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactDAOTest {

    private ContactDAO contactDAO;

    @BeforeEach
    public void setUp() {
        contactDAO = new ContactDAO(); // Membuat instance baru dari ContactDAO
    }

    @BeforeEach
    void clearTable() throws SQLException {
        // Menghapus data di tabel contacts sebelum setiap pengujian
        try (Connection conn = DatabaseConnection.getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM contacts");
        }
    }

    // 1. Pengujian Create (Menambahkan kontak baru)
    @Test
    void testCreateContact() throws SQLException {
        Contact contact = new Contact(0, "John Doe", "path/to/photo.jpg", Arrays.asList("1234567890"));
        contactDAO.addContact(contact);

        List<Contact> contacts = contactDAO.getAllContacts();
        assertEquals(1, contacts.size()); // Pastikan ada 1 kontak

        Contact retrievedContact = contacts.get(0);
        assertEquals("John Doe", retrievedContact.getName());
        assertEquals("path/to/photo.jpg", retrievedContact.getPhotoPath());
        assertEquals(Arrays.asList("1234567890"), retrievedContact.getPhoneNumbers());
    }

    // 2. Pengujian Read (Mengambil semua kontak)
    @Test
    void testReadAllContacts() throws SQLException {
        contactDAO.addContact(new Contact(0, "Alice", "photo1.jpg", Arrays.asList("1111111111")));
        contactDAO.addContact(new Contact(0, "Bob", "photo2.jpg", Arrays.asList("2222222222")));

        List<Contact> contacts = contactDAO.getAllContacts();
        assertEquals(2, contacts.size()); // Pastikan ada 2 kontak

        assertEquals("Alice", contacts.get(0).getName());
        assertEquals("Bob", contacts.get(1).getName());
    }

    // 3. Pengujian Update (Memperbarui data kontak)
    @Test
    void testUpdateContact() throws SQLException {
        Contact contact = new Contact(0, "Charlie", "photo.jpg", Arrays.asList("3333333333"));
        contactDAO.addContact(contact);

        // Ambil kontak dan perbarui nama dan nomor telepon
        List<Contact> contacts = contactDAO.getAllContacts();
        Contact contactToUpdate = contacts.get(0);
        contactToUpdate.setName("Charlie Updated");
        contactToUpdate.setPhoneNumbers(Arrays.asList("4444444444"));

        contactDAO.updateContact(contactToUpdate);

        // Verifikasi pembaruan
        List<Contact> updatedContacts = contactDAO.getAllContacts();
        Contact updatedContact = updatedContacts.get(0);
        assertEquals("Charlie Updated", updatedContact.getName());
        assertEquals(Arrays.asList("4444444444"), updatedContact.getPhoneNumbers());
    }

    // 4. Pengujian Delete (Menghapus kontak)
    @Test
    void testDeleteContact() throws SQLException {
        Contact contact = new Contact(0, "Dave", "photo.jpg", Arrays.asList("5555555555"));
        contactDAO.addContact(contact);

        // Pastikan ada 1 kontak sebelum dihapus
        List<Contact> contacts = contactDAO.getAllContacts();
        assertEquals(1, contacts.size());

        // Hapus kontak
        contactDAO.deleteContact(contacts.get(0).getId());

        // Pastikan tabel kosong setelah penghapusan
        List<Contact> contactsAfterDeletion = contactDAO.getAllContacts();
        assertTrue(contactsAfterDeletion.isEmpty());
    }
}
