package Class;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JadwalManager implements CRUD {
    // Membuat objek dbHelper untuk koneksi ke database
    private final DatabaseHelper dbHelper = new DatabaseHelper();
    private Scanner scanner = new Scanner(System.in);

    // Validasi format tanggal (yyyy-MM-dd)
    private boolean isValidDate(String date) {
        String regex = "^\\d{4}-\\d{2}-\\d{2}$"; // Regex untuk format tanggal
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(date); 
        return matcher.matches(); // Mengembalikan true jika format tanggal sesuai
    }

    // Validasi bulan dalam format tanggal (1-12)
    private boolean isValidMonth(int month) {
        return month >= 1 && month <= 12; // Memastikan bulan valid antara 1 sampai 12
    }

    // Validasi hari berdasarkan bulan dan tahun
    private boolean isValidDay(int year, int month, int day) {
        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0); // Memeriksa tahun kabisat
        
        // Menyimpan jumlah hari dalam setiap bulan, dengan penyesuaian untuk tahun kabisat
        int[] daysInMonth = {31, (isLeapYear ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        return day >= 1 && day <= daysInMonth[month - 1]; // Memeriksa apakah hari valid dalam bulan tertentu
    }

    private boolean isValidTimeRange(String timeRange) {
        String regex = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9])\\s*-\\s*([01]?[0-9]|2[0-3]):([0-5]?[0-9])$"; 
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(timeRange);
        return matcher.matches();
    }
    
    private boolean isTimeConflict(String tanggal, String waktuMulai, String waktuSelesai, Connection conn) throws SQLException {
        // Cek bentrokan waktu pada tanggal yang sama
        String query = "SELECT * FROM kegiatan WHERE tanggal = ? AND ((waktu_mulai <= ? AND waktu_selesai > ?) OR (waktu_mulai < ? AND waktu_selesai >= ?))";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, tanggal);
            ps.setString(2, waktuSelesai);
            ps.setString(3, waktuSelesai);
            ps.setString(4, waktuMulai);
            ps.setString(5, waktuMulai);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Jika ada hasil, berarti ada bentrokan waktu
        }
    }
    
    @Override
    public void create() {
        try (Connection conn = dbHelper.connect()) {
            System.out.println("Pilih jenis kegiatan:");
            System.out.println("1. Kegiatan Olahraga");
            System.out.println("2. Kegiatan Seni");
            System.out.println("3. Seminar");
            System.out.print("Masukkan pilihan (1/2/3): ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // clear buffer
    
            System.out.print("Masukkan nama kegiatan: ");
            String namaKegiatan = scanner.nextLine();
    
            // Validate date input
            String tanggal;
            while (true) {
                System.out.print("Masukkan tanggal kegiatan (yyyy-MM-dd): ");
                tanggal = scanner.nextLine();
                if (isValidDate(tanggal)) {
                    String[] dateParts = tanggal.split("-");
                    int year = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int day = Integer.parseInt(dateParts[2]);
    
                    if (isValidMonth(month) && isValidDay(year, month, day)) {
                        break;
                    } else {
                        System.out.println("Tanggal tidak valid. Periksa bulan dan hari.");
                    }
                } else {
                    System.out.println("Format tanggal tidak valid. Harap masukkan dalam format yyyy-MM-dd.");
                }
            }
    
            // Validate time range input
            String waktuMulai, waktuSelesai;
            while (true) {
                System.out.print("Masukkan waktu mulai dan selesai (HH:mm - HH:mm): ");
                String waktuRange = scanner.nextLine();
                if (isValidTimeRange(waktuRange)) {
                    String[] waktuParts = waktuRange.split("\\s*-\\s*");
                    waktuMulai = waktuParts[0];
                    waktuSelesai = waktuParts[1];
    
                    // Validasi durasi minimal 1 jam
                    String[] mulaiParts = waktuMulai.split(":");
                    String[] selesaiParts = waktuSelesai.split(":");
                    int mulaiJam = Integer.parseInt(mulaiParts[0]);
                    int mulaiMenit = Integer.parseInt(mulaiParts[1]);
                    int selesaiJam = Integer.parseInt(selesaiParts[0]);
                    int selesaiMenit = Integer.parseInt(selesaiParts[1]);
    
                    int durasiMenit = ((selesaiJam * 60 + selesaiMenit) - (mulaiJam * 60 + mulaiMenit));
    
                    if (durasiMenit < 60) {
                        System.out.println("Durasi kegiatan harus minimal 1 jam.");
                        continue;
                    }
    
                    // Validasi bentrokan waktu dengan kegiatan lain pada tanggal yang sama
                    if (isTimeConflict(tanggal, waktuMulai, waktuSelesai, conn)) {
                        System.out.println("Waktu kegiatan bertabrakan dengan kegiatan lain pada tanggal yang sama. Silakan pilih waktu lain.");
                        continue;
                    }
    
                    break;
                } else {
                    System.out.println("Format waktu tidak valid. Harap masukkan dalam format HH:mm - HH:mm.");
                }
            }
    
            System.out.print("Masukkan lokasi kegiatan: ");
            String lokasi = scanner.nextLine();
    
            // Handling different event types
            switch (pilihan) {
                case 1: // Olahraga
                    System.out.print("Masukkan jenis olahraga: ");
                    String jenisOlahraga = scanner.nextLine();
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO kegiatan (nama_kegiatan, jenis_olahraga, tanggal, waktu_mulai, waktu_selesai, lokasi) VALUES (?, ?, ?, ?, ?, ?)")) {
                        ps.setString(1, namaKegiatan);
                        ps.setString(2, jenisOlahraga);
                        ps.setDate(3, Date.valueOf(tanggal));
                        ps.setString(4, waktuMulai);
                        ps.setString(5, waktuSelesai);
                        ps.setString(6, lokasi);
                        ps.executeUpdate();
                        System.out.println("Kegiatan Olahraga berhasil ditambahkan.");
                    }
                    break;
                case 2: // Seni
                    System.out.print("Masukkan jenis seni: ");
                    String jenisSeni = scanner.nextLine();
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO kegiatan (nama_kegiatan, jenis_seni, tanggal, waktu_mulai, waktu_selesai, lokasi) VALUES (?, ?, ?, ?, ?, ?)")) {
                        ps.setString(1, namaKegiatan);
                        ps.setString(2, jenisSeni);
                        ps.setDate(3, Date.valueOf(tanggal));
                        ps.setString(4, waktuMulai);
                        ps.setString(5, waktuSelesai);
                        ps.setString(6, lokasi);
                        ps.executeUpdate();
                        System.out.println("Kegiatan Seni berhasil ditambahkan.");
                    }
                    break;
                case 3: // Seminar
                    System.out.print("Masukkan pembicara seminar: ");
                    String pembicara = scanner.nextLine();
                    System.out.print("Masukkan tema seminar: ");
                    String tema = scanner.nextLine();
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO kegiatan (nama_kegiatan, pembicara, tema, tanggal, waktu_mulai, waktu_selesai, lokasi) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                        ps.setString(1, namaKegiatan);
                        ps.setString(2, pembicara);
                        ps.setString(3, tema);
                        ps.setDate(4, Date.valueOf(tanggal));
                        ps.setString(5, waktuMulai);
                        ps.setString(6, waktuSelesai);
                        ps.setString(7, lokasi);
                        ps.executeUpdate();
                        System.out.println("Seminar berhasil ditambahkan.");
                    }
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menambahkan kegiatan: " + e.getMessage());
        }
    }
    

    @Override
public void read() {
    try (Connection conn = dbHelper.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM kegiatan")) {

        System.out.println("\nDaftar Kegiatan:");
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-5s %-20s %-10s %-10s %-15s %-10s\n", "ID", "Nama", "Tanggal", "Waktu", "Lokasi", "Durasi");
        System.out.println("----------------------------------------------------------");

        int totalKegiatan = 0;
        long totalDurasi = 0; // Untuk menghitung total durasi dalam detik
        while (rs.next()) { // Menampilkan data kegiatan
            int id = rs.getInt("id");
            String nama = rs.getString("nama_kegiatan");
            Date tanggal = rs.getDate("tanggal");
            String waktuMulai = rs.getString("waktu_mulai");
            String waktuSelesai = rs.getString("waktu_selesai");
            String lokasi = rs.getString("lokasi");

            // Menghitung durasi kegiatan (waktuMulai dan waktuSelesai dalam format HH:mm:ss)
            String[] waktuMulaiParts = waktuMulai.split(":");
            String[] waktuSelesaiParts = waktuSelesai.split(":");

            int mulaiJam = Integer.parseInt(waktuMulaiParts[0]);
            int mulaiMenit = Integer.parseInt(waktuMulaiParts[1]);
            int selesaiJam = Integer.parseInt(waktuSelesaiParts[0]);
            int selesaiMenit = Integer.parseInt(waktuSelesaiParts[1]);

            // Menghitung durasi dalam menit
            long durasi = (selesaiJam * 60 + selesaiMenit) - (mulaiJam * 60 + mulaiMenit);
            totalDurasi += durasi; // Menambahkan durasi kegiatan ke total durasi
            totalKegiatan++;

            System.out.printf("%-5d %-20s %-10s %-5s - %-5s %-15s %-10d\n", id, nama, tanggal, waktuMulai, waktuSelesai, lokasi, durasi);
        }

        // Menampilkan total kegiatan dan total durasi dalam menit
        System.out.println("----------------------------------------------------------");
        System.out.println("Total Kegiatan: " + totalKegiatan);
        System.out.println("Total Durasi Kegiatan: " + totalDurasi + " menit");

        // Menghitung rata-rata durasi kegiatan
        if (totalKegiatan > 0) {
            long rataRataDurasi = totalDurasi / totalKegiatan;
            System.out.println("Rata-rata Durasi Kegiatan: " + rataRataDurasi + " menit");
        } else {
            System.out.println("Tidak ada kegiatan.");
        }

        System.out.println("----------------------------------------------------------");

    } catch (SQLException e) {
        System.out.println("Terjadi kesalahan saat membaca data: " + e.getMessage());
    }
}




    @Override
    public void update() {
        System.out.print("Masukkan ID kegiatan yang ingin diubah: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer input
    
        try (Connection conn = dbHelper.connect()) {
            // Memeriksa apakah ID ada
            String checkQuery = "SELECT * FROM kegiatan WHERE id = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(checkQuery)) {
                psCheck.setInt(1, id);
                ResultSet rs = psCheck.executeQuery();
    
                if (!rs.next()) {
                    System.out.println("Kegiatan dengan ID tersebut tidak ditemukan.");
                    return;
                }
    
                // Data awal untuk konfirmasi
                System.out.println("\nKegiatan yang akan diubah:");
                System.out.println("Nama: " + rs.getString("nama_kegiatan"));
                System.out.println("Tanggal: " + rs.getDate("tanggal"));
                System.out.println("Waktu: " + rs.getString("waktu_mulai") + " - " + rs.getString("waktu_selesai"));
                System.out.println("Lokasi: " + rs.getString("lokasi"));
    
                // Input perubahan data
                System.out.print("Masukkan nama kegiatan baru (kosong untuk tidak mengubah): ");
                String namaBaru = scanner.nextLine();
    
                System.out.print("Masukkan tanggal baru (yyyy-MM-dd, kosong untuk tidak mengubah): ");
                String tanggalBaru = scanner.nextLine();
    
                System.out.print("Masukkan waktu mulai baru (HH:mm, kosong untuk tidak mengubah): ");
                String waktuMulaiBaru = scanner.nextLine();
    
                System.out.print("Masukkan waktu selesai baru (HH:mm, kosong untuk tidak mengubah): ");
                String waktuSelesaiBaru = scanner.nextLine();
    
                System.out.print("Masukkan lokasi baru (kosong untuk tidak mengubah): ");
                String lokasiBaru = scanner.nextLine();
    
                // Membangun query dinamis
                StringBuilder updateQuery = new StringBuilder("UPDATE kegiatan SET ");
                boolean hasUpdates = false;
    
                if (!namaBaru.isEmpty()) {
                    updateQuery.append("nama_kegiatan = ?, ");
                    hasUpdates = true;
                }
                if (!tanggalBaru.isEmpty()) {
                    updateQuery.append("tanggal = ?, ");
                    hasUpdates = true;
                }
                if (!waktuMulaiBaru.isEmpty()) {
                    updateQuery.append("waktu_mulai = ?, ");
                    hasUpdates = true;
                }
                if (!waktuSelesaiBaru.isEmpty()) {
                    updateQuery.append("waktu_selesai = ?, ");
                    hasUpdates = true;
                }
                if (!lokasiBaru.isEmpty()) {
                    updateQuery.append("lokasi = ?, ");
                    hasUpdates = true;
                }
    
                if (!hasUpdates) {
                    System.out.println("Tidak ada perubahan yang dilakukan.");
                    return;
                }
    
                // Menghapus koma terakhir
                updateQuery.setLength(updateQuery.length() - 2);
                updateQuery.append(" WHERE id = ?");
    
                // Menyiapkan query untuk eksekusi
                try (PreparedStatement psUpdate = conn.prepareStatement(updateQuery.toString())) {
                    int paramIndex = 1;
    
                    if (!namaBaru.isEmpty()) psUpdate.setString(paramIndex++, namaBaru);
                    if (!tanggalBaru.isEmpty()) psUpdate.setDate(paramIndex++, Date.valueOf(tanggalBaru));
                    if (!waktuMulaiBaru.isEmpty()) psUpdate.setString(paramIndex++, waktuMulaiBaru);
                    if (!waktuSelesaiBaru.isEmpty()) psUpdate.setString(paramIndex++, waktuSelesaiBaru);
                    if (!lokasiBaru.isEmpty()) psUpdate.setString(paramIndex++, lokasiBaru);
    
                    psUpdate.setInt(paramIndex, id); // Parameter terakhir untuk ID
    
                    // Eksekusi query
                    psUpdate.executeUpdate();
                    System.out.println("Kegiatan berhasil diperbarui.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat memperbarui data: " + e.getMessage());
        }
    }
    

    @Override
    public void delete() {
        // Mengambil ID kegiatan yang ingin dihapus
        System.out.print("Masukkan ID kegiatan yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer input

        try (Connection conn = dbHelper.connect()) {
            // Menghapus data kegiatan dari database
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM kegiatan WHERE id = ?")) {
                ps.setInt(1, id);
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Kegiatan berhasil dihapus.");
                } else {
                    System.out.println("ID kegiatan tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menghapus kegiatan: " + e.getMessage());
        }
    }

 
}
