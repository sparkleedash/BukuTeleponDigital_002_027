package com.example.bukutelepondigital;

import java.util.List;

public class Contact {
    private int id;
    private String name;
    private String photoPath;
    private List<String> phoneNumbers;

    // Constructor
    public Contact(int id, String name, String photoPath, List<String> phoneNumbers) {
        this.id = id;
        this.name = name;
        this.photoPath = photoPath;
        this.phoneNumbers = phoneNumbers;
    }

    // Getter dan Setter untuk id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter dan Setter untuk name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter dan Setter untuk photoPath
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    // Getter dan Setter untuk phoneNumbers
    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    // Untuk memudahkan dalam debugging dan menampilkan informasi kontak
    @Override
    public String toString() {
        return "Contact{id=" + id + ", name='" + name + "', photoPath='" + photoPath + "', phoneNumbers=" + phoneNumbers + '}';
    }
}
