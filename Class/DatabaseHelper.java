package Class;

import java.sql.Connection; // Mengimpor kelas Connection dari package java.sql untuk berinteraksi dengan database
import java.sql.DriverManager; // Mengimpor kelas DriverManager untuk mengelola koneksi JDBC
import java.sql.SQLException; // Mengimpor kelas SQLException untuk menangani kesalahan SQL

public class DatabaseHelper {
    // Mendeklarasikan URL koneksi ke database. URL ini digunakan untuk menghubungkan aplikasi dengan database MySQL.
    private final String URL = "jdbc:mysql://localhost:3306/event_management"; // Ganti dengan URL database Anda

    // Mendeklarasikan username untuk mengakses database
    private final String USER = "root"; // Ganti dengan username database Anda

    // Mendeklarasikan password untuk mengakses database
    private final String PASSWORD = "Staylearn01*"; // Ganti dengan password database Anda

    // Metode untuk membuat koneksi ke database menggunakan JDBC
    public Connection connect() throws SQLException {
        // Menggunakan DriverManager untuk menghubungkan aplikasi ke database MySQL
        return DriverManager.getConnection(URL, USER, PASSWORD); // Koneksi berhasil jika tidak ada error
    }
}
