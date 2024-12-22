package Class;

import java.text.SimpleDateFormat;  // Mengimpor kelas SimpleDateFormat untuk memformat tanggal
import java.util.Date;              // Mengimpor kelas Date untuk menangani tanggal

// Kelas Kegiatan merupakan kelas induk (superclass) yang digunakan oleh kelas-kelas turunan seperti KegiatanOlahraga, KegiatanSeminar, dan KegiatanSeni
public class Kegiatan {
    // Deklarasi variabel yang akan menyimpan data terkait kegiatan
    protected int id;               // ID unik untuk setiap kegiatan
    protected String namaKegiatan;  // Nama kegiatan
    protected Date tanggal;         // Tanggal kegiatan
    protected String waktuMulai;    // Waktu mulai kegiatan
    protected String waktuSelesai;  // Waktu selesai kegiatan
    protected String lokasi;        // Lokasi kegiatan

    // Konstruktor Kegiatan untuk menginisialisasi objek dengan parameter yang diberikan
    public Kegiatan(int id, String namaKegiatan, Date tanggal, String waktuMulai, String waktuSelesai, String lokasi) {
        this.id = id;
        this.namaKegiatan = namaKegiatan;
        this.tanggal = tanggal;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.lokasi = lokasi;
    }

    // Getter untuk ID kegiatan
    public int getId() {
        return id;
    }

    // Getter untuk nama kegiatan
    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    // Getter untuk tanggal kegiatan, yang diformat menjadi "dd-MM-yyyy"
    public String getTanggal() {
        return new SimpleDateFormat("dd-MM-yyyy").format(tanggal);  // Format tanggal menjadi format yang lebih mudah dibaca
    }

    // Getter untuk waktu mulai kegiatan
    public String getWaktuMulai() {
        return waktuMulai;
    }

    // Getter untuk waktu selesai kegiatan
    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    // Getter untuk lokasi kegiatan
    public String getLokasi() {
        return lokasi;
    }
}
