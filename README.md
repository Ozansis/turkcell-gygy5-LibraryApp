# 📚 Archivist - Kütüphane Takip Uygulaması
Bu proje, Android uygulama geliştirme eğitimi kapsamında, modern teknolojiler (Jetpack Compose & Supabase) kullanılarak geliştirilmiş bir **Kütüphane Yönetim Sistemi** uygulamasıdır. Temel amacı, bir öğrencinin kütüphanedeki kitapları incelemesi, ödünç alması ve kendi kiralama geçmişini takip etmesini sağlamaktır.

## 🚀 Öne Çıkan Özellikler
*   **Üye Ol & Giriş Yap:** Supabase Auth kullanarak güvenli kullanıcı kaydı ve giriş sistemi.
*   **Kitap Arşivi:** Kütüphanedeki tüm kitapları 2'li grid yapısında, şık kartlarla listeleme.
*   **Akıllı Arama:** Kitap ismine göre anlık arama yapabilme.
*   **Ödünç Alma (Borrow):** Kitap detay sayfasından stok durumuna göre kitabı 5 günlüğüne ödünç alma.
*   **Profilim:** Kullanıcının aktif olarak elinde bulunan kitapları ve iade tarihini (kaç gün kaldığını) görmesi.
*   **Kiralama Geçmişi:** Daha önce alınan kitapların listesi ve iade durumlarının (Returned, Overdue vb.) takibi.

## 📸 Ekran Görüntüleri

<div align="center">
  <img src="https://github.com/user-attachments/assets/e93fd6f3-b6b2-40e7-aa66-573d1528a54a" width="160"/>
  <img src="https://github.com/user-attachments/assets/94b2674a-3a0d-4117-b8c6-c478a81f652d" width="160"/>
  <img src="https://github.com/user-attachments/assets/fda327e0-40bd-43d2-95a2-6980cd250037" width="160"/>
  <img src="https://github.com/user-attachments/assets/05232de9-0e9f-45e2-90d7-4383f276e7e9" width="160"/>
  <img src="https://github.com/user-attachments/assets/c65f8a8d-e16d-48e5-9a4f-3730f285e8ee" width="160"/>
  <img src="https://github.com/user-attachments/assets/8b7ff843-3ac1-493d-81c3-716394d6d9c2" width="160"/>
</div>

## 🛠️ Kullanılan Teknolojiler
Bu projede güncel Android geliştirme standartları uygulanmıştır:
*   **Jetpack Compose:** Tamamen deklaratif UI yapısı (Material 3).
*   **MVVM Mimari:** Temiz kod ve mantıksal ayrım için ViewModel kullanımı.
*   **Supabase:** Veritabanı (PostgreSQL) ve kimlik doğrulama (Auth) işlemleri için.
*   **Kotlin Coroutines & Flow:** Asenkron veri akışları ve performanslı çalışma.
*   **Kotlinx Serialization:** JSON veri dönüşümleri.
*   **Navigation Compose:** Sayfalar arası akış yönetimi.

## 📂 Proje Yapısı
*   `ui/screen`: Tüm ekranların (Login, Home, Profile, History vb.) tasarım kodları.
*   `ui/viewmodel`: Ekranların mantıksal işlemlerini yöneten ViewModel sınıfları.
*   `data/repository`: Supabase ile iletişim kuran veri sınıfları.
*   `data/model`: Kitap ve Kiralama verilerini temsil eden Data Class'lar.
*   `ui/navigation`: Uygulamanın sayfa rotaları ve NavGraph yapısı.

## ⚙️ Nasıl Çalıştırılır?
1.  Projeyi Android Studio ile açın.
2.  `local.properties` içine kendi Supabase URL ve Key bilgilerinizi ekleyin.
3.  Gradle senkronizasyonunu yapın.
4.  Uygulamayı bir emülatörde veya fiziksel cihazda çalıştırın.
