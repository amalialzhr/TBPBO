package Class;

import java.util.ArrayList;  // Mengimpor ArrayList, yang merupakan bagian dari Collection Framework
import java.util.Date;       // Mengimpor kelas Date untuk menangani tanggal

// Kelas KegiatanSeminar adalah subclass dari Kegiatan (Inheritance)
public class KegiatanSeminar extends Kegiatan {
    // Deklarasi variabel 'tema' untuk menyimpan tema seminar dan 'pembicaraList' untuk daftar pembicara
    private String tema;
    private ArrayList<String> pembicaraList;  // Collection Framework - List of speakers

    // Konstruktor untuk KegiatanSeminar, yang menerima parameter untuk menginisialisasi properti kelas
    public KegiatanSeminar(int id, String namaKegiatan, Date tanggal, String waktuMulai, String waktuSelesai, String lokasi, String tema) {
        super(id, namaKegiatan, tanggal, waktuMulai, waktuSelesai, lokasi);  // Memanggil konstruktor superclass 'Kegiatan'
        this.tema = tema;  // Menyimpan tema seminar
        this.pembicaraList = new ArrayList<>();  // Inisialisasi ArrayList untuk pembicara
    }

    // Getter untuk tema, digunakan untuk mendapatkan informasi tentang tema seminar
    public String getTema() {
        return tema;
    }

    // Method untuk menambahkan pembicara ke dalam daftar pembicara (manipulasi Collection Framework)
    public void addPembicara(String pembicara) {
        pembicaraList.add(pembicara);  // Menambahkan pembicara ke dalam ArrayList
    }

    // Getter untuk pembicaraList, digunakan untuk mengambil daftar pembicara
    public ArrayList<String> getPembicaraList() {
        return pembicaraList;  // Mengembalikan daftar pembicara
    }
}
