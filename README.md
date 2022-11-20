# What-Movie

Challenge Binar Academy Chapter 8

Kriteria :
1. Generate aplikasi yang sudah siap dipublish
2. Menerapkan Lint : Ignore hardcoded text di fragment_home.xml, missing translation, supress lint main activity, ignore warning di gradle
3. Implementasi CI/CD menggunakan CircleCI

-------------------------------------------------------------------------------------

Note:
1. Di change profile picture langsung dilakukan operasi workmanager.
2. Hasil workmanager disimpan di galeri, ada 2 file, sebelum blur dan sesudah blur.
3. Foto profil masih disimpan di Datastore, belum disimpan di Room database.
4. !!! Button login google adalah button bug untuk crashlytics !!!

-------------------------------------------------------------------------------------

Challenge Binar Academy Chapter 7

Kriteria :
1. Menggunakan Firebase untuk Analytics dan Crashlitics
2. Analisa Profiler
3. Unit testing : AppDatabaseTest, MoviesRepositoryTest, HomeActivityViewModelTest, LoginViewModelTest, RegisterViewModelTest
4. Build Flavors : Production, Free, Pro (Berbeda nama + logo saja)

-------------------------------------------------------------------------------------

Challenge Binar Academy Chapter 6

Kriteria :
1. Menggunakan WorkManager untuk blur foto profil.
2. Menerapkan design pattern MVVM.
3. Melakukan penyimpanan data lokal dengan DataStore.
4. Dapat menambahkan foto profil.
5. Menerapkan DI Hilt.

-------------------------------------------------------------------------------------

Challenge Binar Academy Chapter 5

Kriteria :
1. API dengan Retrofit
2. Android Permission : Internet
3. Komponen material design : Cardview, TextInputLayout
4. Localization : Bahasa Indonesia, Spanish
5. Styling : Format teks untuk headline, headline desc, username, ada di styles.xml

-------------------------------------------------------------------------------------

Known Bugs :
1. Saat pertama kali login, tombol log out di profil harus dipencet lagi 2 kali untuk log out.
