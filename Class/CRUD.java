package Class;

// Mendeklarasikan interface CRUD yang mendefinisikan empat metode dasar untuk operasi database atau manipulasi data
public interface CRUD {
    // Metode untuk menambah data baru
    void create();

    // Metode untuk membaca atau menampilkan data
    void read();

    // Metode untuk memperbarui data yang sudah ada
    void update();

    // Metode untuk menghapus data
    void delete();
}
