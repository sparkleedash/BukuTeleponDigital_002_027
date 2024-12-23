# BUKU TELEPON DIGITAL
Program aplikasi buku telepon digital menggunaka

---

## 📝 Deskripsi Program

Program ini adalah aplikasi buku telepon digital yang memungkinkan pengguna untuk menyimpan, melihat, mengedit, dan menghapus kontak. Program ini dibuat menggunakan JavaFX untuk antarmuka pengguna (GUI) dan SQLite untuk penyimpanan database. Setiap kontak dapat disimpan dengan informasi berupa nama, nomor telepon, dan gambar. Aplikasi ini memiliki antarmuka yang mudah digunakan untuk mengelola kontak pribadi.

---

## 🎯 Fitur Utama

1. **Menambah Kontak**:
   - Pengguna dapat menambah kontak baru dengan memasukkan nama dan nomor telepon.
   - Nama dibatasi maksimal 10 karakter dan hanya boleh menggunakan huruf.
   - Nomor telepon harus dimulai dengan '08' dan memiliki panjang maksimal 12 digit.
   - Data kontak disimpan dalam database menggunakan ADO dan JDBC.

2. **Melihat Kontak**:
   - Pengguna dapat melihat daftar seluruh kontak yang telah disimpan dari database.
   - Daftar ini menampilkan nama dan nomor telepon dari database.

3. **Mengedit Kontak**:
   - Pengguna dapat mengedit informasi kontak yang sudah ada yang ada dalam database.

4. **Menghapus Kontak**:
   - Pengguna dapat menghapus kontak dengan memilih tombol "Delete". Aplikasi akan menampilkan konfirmasi penghapusan.
   - Setelah konfirmasi, jika pengguna memilih "Yes", kontak akan dihapus dari database. Jika memilih "No", penghapusan dibatalkan.

5. **Validasi Input**:
   - Nama kontak hanya dapat berisi huruf dan memiliki panjang maksimal 10 karakter.
   - Nomor telepon harus diawali dengan '08' dan memiliki panjang maksimal 12 digit.

6. **Antarmuka Pengguna**:
   - Antarmuka grafis pengguna menggunakan Java Swing untuk desain GUI yang interaktif dan mudah digunakan.
   - Pengguna dapat mengakses menu utama untuk menambah kontak, melihat kontak, atau keluar dari aplikasi.

---

## 🛠 Teknologi yang Digunakan

- **Bahasa Pemrograman**: Java
- **GUI Framework**: Java Swing
- **Database**: MySQL atau SQLite
- **JDBC**: Digunakan untuk menghubungkan aplikasi dengan database menggunakan koneksi JDBC.
- **ADO**: Digunakan untuk berinteraksi dengan database, mengelola penyimpanan data kontak.
- **Validasi Input**: Nama dan nomor telepon kontak divalidasi untuk memastikan data yang dimasukkan sesuai dengan format yang ditentukan.

---

## 🚀 Panduan Instalasi

### Persyaratan
- **JDK (Java Development Kit)** versi 8 atau lebih baru.
- **IDE** (Integrated Development Environment) seperti IntelliJ IDEA atau Eclipse.
- **Database**: Pastikan Anda telah menginstal MySQL atau SQLite dan memiliki akses ke database.
- **Driver JDBC**: Anda perlu menginstal driver JDBC yang sesuai untuk MySQL atau SQLite.
---

### Langkah Instalasi
1. **Clone Repositori**:
   ```bash
   git clone https://github.com/username/repository.git
2. **Buka Proyek di IDE**:
   - Buka proyek ini menggunakan IDE yang Anda pilih (misalnya IntelliJ IDEA, Eclipse).
3. **Konfigurasi Database**:
   - Pastikan Anda sudah menginstal MySQL atau SQLite dan mengonfigurasi database.
   - Buat database baru bernama phonebook di MySQL atau SQLite, atau sesuaikan nama database dalam kode program.
   - Buat tabel untuk menyimpan kontak.
4. **Menambahkan Driver JDBC**:
  - Unduh dan tambahkan driver JDBC untuk MySQL atau SQLite ke dalam proyek Anda.
  - Untuk MySQL, Anda dapat mengunduh MySQL JDBC Driver.
  - Untuk SQLite, Anda dapat menggunakan SQLite JDBC Driver.
5. **Menambahkan Koneksi Database**:
  - Pastikan Anda mengonfigurasi koneksi database dengan benar di dalam file DatabaseConnector.java.
  - Anda dapat menyesuaikan URL koneksi dan kredensial sesuai dengan konfigurasi database Anda.
6. **Jalankan Program**:
  - Setelah menyiapkan database dan driver JDBC, buka file Main.java, yang merupakan titik masuk utama aplikasi.
  - Jalankan aplikasi dengan menekan tombol "Run" di IDE.
  - Aplikasi akan memulai antarmuka grafis dan Anda dapat menambah, melihat, mengedit, dan menghapus kontak yang disimpan dalam database.
7. **Menambah Kontak**:
  -Klik tombol "Add Contact" untuk menambah kontak baru.
  -Masukkan nama (maksimal 10 karakter, hanya huruf) dan nomor telepon (dimulai dengan '08' dan maksimal 12 digit).
  -Klik "Save" untuk menyimpan ke database atau "Back" untuk kembali ke menu utama.
8. **Melihat Kontak**:
  - Klik tombol "View Contacts" untuk melihat daftar kontak yang tersimpan dalam database.
  - Kontak ditampilkan dengan nama dan nomor telepon yang disimpan di database.
9. **Mengedit Kontak**:
  - Pilih kontak yang ingin diedit dan klik tombol "Edit".
  - Edit nama dan nomor telepon, kemudian klik "Save" untuk menyimpan perubahan ke database.
10. **Menghapus Kontak**:
  - Klik tombol "Delete" pada kontak yang ingin dihapus.
  - Aplikasi akan menampilkan konfirmasi penghapusan. Pilih "Yes" untuk menghapus atau "No" untuk membatalkan.
---


## 📄 Lisensi

Program ini dibuat untuk tujuan edukasi dan dapat digunakan secara bebas. Jika Anda mengembangkan program ini lebih lanjut, harap berikan atribusi kepada pengembang asli.

---

Terima kasih telah menggunakan aplikasi Buku Telepon Digital! 🎉

