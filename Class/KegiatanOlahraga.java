package Class;

import java.util.ArrayList;  // Mengimpor ArrayList, yang merupakan bagian dari Collection Framework
import java.util.Date;       // Mengimpor kelas Date untuk menangani tanggal

// Kelas KegiatanOlahraga adalah subclass dari Kegiatan (Inheritance)
public class KegiatanOlahraga extends Kegiatan {
    // Deklarasi variabel 'jenisOlahraga' untuk menyimpan jenis olahraga dan 'olahragaList' untuk daftar olahraga
    private String jenisOlahraga;
    private ArrayList<String> olahragaList;  // Collection Framework - List of sports

    // Konstruktor untuk KegiatanOlahraga, yang menerima parameter untuk menginisialisasi properti kelas
    public KegiatanOlahraga(int id, String namaKegiatan, Date tanggal, String waktuMulai, String waktuSelesai, String lokasi, String jenisOlahraga) {
        super(id, namaKegiatan, tanggal, waktuMulai, waktuSelesai, lokasi);  // Memanggil konstruktor superclass 'Kegiatan'
        this.jenisOlahraga = jenisOlahraga;  // Menyimpan jenis olahraga
        this.olahragaList = new ArrayList<>();  // Inisialisasi ArrayList untuk olahraga
    }

    // Getter untuk jenisOlahraga, digunakan untuk mendapatkan informasi tentang jenis olahraga
    public String getJenisOlahraga() {
        return jenisOlahraga;
    }

    // Method untuk menambahkan olahraga ke dalam daftar olahraga (manipulasi Collection Framework)
    public void addOlahraga(String olahraga) {
        olahragaList.add(olahraga);  // Menambahkan olahraga ke dalam ArrayList
    }

    // Getter untuk olahragaList, digunakan untuk mengambil daftar olahraga
    public ArrayList<String> getOlahragaList() {
        return olahragaList;  // Mengembalikan daftar olahraga
    }
}
