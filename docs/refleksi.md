# Refleksi Teknis

## Keputusan desain

Aku memakai **Room** untuk menyimpan data karena aplikasi harus tetap bisa dipakai tanpa
internet dan datanya tidak boleh hilang saat aplikasi ditutup. Variabel biasa tidak bisa
memenuhi itu, sedangkan Room menyimpan data di database SQLite lokal dan memberi hasil
berupa `Flow`, sehingga daftar di layar otomatis ter-update ketika data berubah.

Aplikasi disusun berlapis: **UI → ViewModel → Repository → DAO → Database**. UI tidak
mengakses database langsung. UI hanya menampilkan state dan mengirim event, sementara akses
data diurus lapisan di bawahnya. Dengan begitu kode lebih mudah dipahami dan diubah, dan
operasi database tidak berjalan di main thread sehingga tampilan tidak macet.

**UI state disimpan di ViewModel** (`StateFlow`) supaya bertahan saat layar diputar. Hanya
state sementara yang murni milik satu layar, seperti isian form dan dialog hapus, yang
disimpan di composable dengan `rememberSaveable`.

Beberapa keputusan lain:
- Layar Tambah dan Edit memakai satu komponen `TugasForm`, jadi form tidak ditulis dua kali.
- Awalnya status tugas hanya bisa diubah dari layar Detail. Aku menambahkannya juga ke form
  Edit supaya semua data tugas bisa diubah di satu tempat, dan chip status di Detail tetap
  ada sebagai jalan pintas.
- Tampilan dibuat **minimalis**: daftar berupa baris tanpa kartu, satu warna aksen, dan
  status berupa titik kecil, agar mudah dibaca di layar kecil.

## Kendala yang dihadapi

1. **Versi library tidak cocok dengan Android Gradle Plugin.** Setelah menambahkan Room,
   Navigation, dan ViewModel, Gradle sync menampilkan 16 error. Penyebabnya, beberapa
   library (core-ktx, activity-compose, lifecycle) meminta AGP dan compileSdk yang lebih
   baru daripada AGP 8.8.0 dan compileSdk 35 milik project. Aku menyelesaikannya dengan
   **menurunkan versi library** agar cocok, bukan menaikkan AGP, karena upgrade AGP bisa
   ikut mengubah Gradle, Kotlin, dan KSP. Pelajarannya: versi library saling bergantung,
   jadi kompatibilitasnya harus dicek sebelum dipakai.
2. **Versi KSP harus cocok dengan versi Kotlin.** Project memakai Kotlin 2.0.0, sehingga
   KSP yang dipakai adalah 2.0.0-1.0.21.
3. **Kesalahan kecil di kode**, misalnya `import padding` yang terlewat. Dari sini aku
   belajar membaca pesan error di tab Build untuk menemukan file dan baris yang bermasalah.
4. **Gambar di README tidak tampil** karena file README berada di dalam folder `docs`
   sehingga path gambarnya salah. Setelah README dipindah ke root repository, gambar tampil.

## Perbaikan berikutnya

- Filter tugas berdasarkan status dan pencarian berdasarkan judul atau mata kuliah.
- Penanda untuk tugas yang tenggatnya dekat atau sudah lewat.
- Pengingat (notifikasi) mendekati deadline.
- Unit test untuk ViewModel dan DAO, karena pengujian saat ini masih manual.
- Migrasi database untuk perubahan skema di masa depan.

## Yang kupelajari

Pemisahan tanggung jawab membuat aplikasi lebih mudah dikembangkan: UI cukup menampilkan
state, sedangkan perubahan data mengalir lewat event ke ViewModel, Repository, dan Room,
lalu kembali ke UI sebagai state baru. Aku juga belajar bahwa mengelola versi dependency dan
membuat commit kecil yang bermakna sama pentingnya dengan menulis kodenya.
