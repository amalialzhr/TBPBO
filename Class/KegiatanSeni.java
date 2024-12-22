package Class;

import java.util.ArrayList;  // Mengimpor ArrayList, yang merupakan bagian dari Collection Framework
import java.util.Date;       // Mengimpor kelas Date untuk menangani tanggal

// Kelas KegiatanSeni adalah subclass dari Kegiatan (Inheritance)
public class KegiatanSeni extends Kegiatan {
    // Deklarasi variabel 'jenisSeni' untuk menyimpan jenis seni dan 'pengisiAcaraList' untuk daftar pengisi acara
    private String jenisSeni;
    private ArrayList<String> pengisiAcaraList;  // Collection Framework - List of performers

    // Konstruktor untuk KegiatanSeni, yang menerima parameter untuk menginisialisasi properti kelas
    public KegiatanSeni(int id, String namaKegiatan, Date tanggal, String waktuMulai, String waktuSelesai, String lokasi, String jenisSeni) {
        super(id, namaKegiatan, tanggal, waktuMulai, waktuSelesai, lokasi);  // Memanggil konstruktor superclass 'Kegiatan'
        this.jenisSeni = jenisSeni;  // Menyimpan jenis seni
        this.pengisiAcaraList = new ArrayList<>();  // Inisialisasi ArrayList untuk pengisi acara
    }

    // Getter untuk jenisSeni, digunakan untuk mendapatkan informasi tentang jenis seni
    public String getJenisSeni() {
        return jenisSeni;
    }

    // Method untuk menambahkan pengisi acara ke dalam daftar pengisi acara (manipulasi Collection Framework)
    public void addPengisiAcara(String pengisiAcara) {
        pengisiAcaraList.add(pengisiAcara);  // Menambahkan pengisi acara ke dalam ArrayList
    }

    // Getter untuk pengisiAcaraList, digunakan untuk mengambil daftar pengisi acara
    public ArrayList<String> getPengisiAcaraList() {
        return pengisiAcaraList;  // Mengembalikan daftar pengisi acara
    }
}
