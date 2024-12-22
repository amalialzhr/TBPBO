package Class;

import java.util.Scanner;  // Mengimpor kelas Scanner untuk membaca input dari pengguna

public class Main {
    public static void main(String[] args) {
        // Membuat objek Scanner untuk menerima input dari pengguna
        Scanner scanner = new Scanner(System.in);
        
        // Membuat objek JadwalManager yang akan digunakan untuk mengelola kegiatan
        JadwalManager jadwalManager = new JadwalManager();
        
        // Mendeklarasikan variabel pilihan untuk menyimpan pilihan menu yang dipilih oleh pengguna
        int pilihan;

        // Melakukan perulangan untuk menampilkan menu dan menerima input pengguna sampai pilihan adalah 5 (Keluar)
        do {
            // Menampilkan menu utama aplikasi
            System.out.println("=== Aplikasi Manajemen Kegiatan ===");
            System.out.println("1. Tambah Kegiatan");  // Menu untuk menambah kegiatan
            System.out.println("2. Lihat Kegiatan");   // Menu untuk melihat kegiatan yang ada
            System.out.println("3. Perbarui Kegiatan"); // Menu untuk memperbarui kegiatan
            System.out.println("4. Hapus Kegiatan");   // Menu untuk menghapus kegiatan
            System.out.println("5. Keluar");            // Menu untuk keluar dari aplikasi
            System.out.print("Masukkan pilihan Anda: ");  // Minta pengguna memasukkan pilihan menu
            pilihan = scanner.nextInt();  // Membaca input pilihan menu dari pengguna
            scanner.nextLine();  // Membersihkan buffer input untuk mencegah error pada input selanjutnya

            // Mengecek pilihan pengguna menggunakan pernyataan switch
            switch (pilihan) {
                case 1:
                    // Jika pilihan adalah 1, jalankan metode create() dari JadwalManager untuk menambah kegiatan
                    jadwalManager.create();
                    break;
                case 2:
                    // Jika pilihan adalah 2, jalankan metode read() dari JadwalManager untuk melihat kegiatan yang ada
                    jadwalManager.read();
                    break;
                case 3:
                    // Jika pilihan adalah 3, jalankan metode update() dari JadwalManager untuk memperbarui kegiatan
                    jadwalManager.update();
                    break;
                case 4:
                    // Jika pilihan adalah 4, jalankan metode delete() dari JadwalManager untuk menghapus kegiatan
                    jadwalManager.delete();
                    break;
                case 5:
                    // Jika pilihan adalah 5, keluar dari program dengan pesan terima kasih
                    System.out.println("Terima kasih! Sampai jumpa.");
                    break;
                default:
                    // Jika pilihan yang dimasukkan tidak valid, tampilkan pesan error
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 5); // Ulangi perulangan sampai pilihan adalah 5 (Keluar)

        // Menutup scanner untuk mencegah memory leak
        scanner.close();
    }
}
