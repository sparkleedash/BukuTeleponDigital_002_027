package com.example.bukutelepondigital;

import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactDAOTest {

    private ContactDAO contactDAO;

    @BeforeEach
    public void setUp() {
        // Inisialisasi objek ContactDAO sebelum setiap test dijalankan
        contactDAO = new ContactDAO();
    }

    @Test
    public void testAddContact() throws SQLException {
        // Membuat objek Contact untuk ditambahkan
        Contact contact = new Contact(0, "John Doe", "path/to/photo.jpg", List.of("1234567890", "0987654321"));

        // Menambahkan kontak menggunakan ContactDAO
        contactDAO.addContact(contact);

        // Mengecek apakah kontak berhasil ditambahkan
        List<Contact> contacts = contactDAO.getAllContacts();
        assertTrue(contacts.stream().anyMatch(c -> c.getName().equals("John Doe")));
    }

    @Test
    public void testGetAllContacts() throws SQLException {
        // Menambahkan kontak terlebih dahulu
        Contact contact1 = new Contact(0, "Alice", "path/to/alice.jpg", List.of("111222333"));
        Contact contact2 = new Contact(0, "Bob", "path/to/bob.jpg", List.of("444555666"));
        contactDAO.addContact(contact1);
        contactDAO.addContact(contact2);

        // Mengambil semua kontak
        List<Contact> contacts = contactDAO.getAllContacts();

        // Mengecek apakah daftar kontak memiliki dua kontak yang ditambahkan
        assertEquals(2, contacts.size());
    }

    @Test
    public void testUpdateContact() throws SQLException {
        // Menambahkan kontak yang akan diperbarui
        Contact contact = new Contact(0, "Charlie", "path/to/charlie.jpg", List.of("777888999"));
        contactDAO.addContact(contact);

        // Mengubah nama dan nomor telepon
        contact.setName("Charles");
        contact.setPhoneNumbers(List.of("999888777"));

        // Memperbarui kontak
        contactDAO.updateContact(contact);

        // Mengambil kontak yang sudah diperbarui
        List<Contact> contacts = contactDAO.getAllContacts();
        assertTrue(contacts.stream().anyMatch(c -> c.getName().equals("Charles")));
    }

    @Test
    public void testDeleteContact() throws SQLException {
        // Menambahkan kontak untuk dihapus
        Contact contact = new Contact(0, "David", "path/to/david.jpg", List.of("555666777"));
        contactDAO.addContact(contact);

        // Mengambil ID kontak pertama
        int contactId = contact.getId();

        // Menghapus kontak
        contactDAO.deleteContact(contactId);

        // Mengecek apakah kontak sudah dihapus
        List<Contact> contacts = contactDAO.getAllContacts();
        assertFalse(contacts.stream().anyMatch(c -> c.getId() == contactId));
    }

    @AfterEach
    public void tearDown() {
        // Bersihkan atau reset data setelah pengujian (misalnya menghapus kontak dari database)
    }
}

