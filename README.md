📚 Archivist - Kütüphane Takip Uygulaması
Bu proje, Android uygulama geliştirme eğitimi kapsamında, modern teknolojiler (Jetpack Compose & Supabase) kullanılarak geliştirilmiş bir Kütüphane Yönetim Sistemi uygulamasıdır. Temel amacı, bir öğrencinin kütüphanedeki kitapları incelemesi, ödünç alması ve kendi kiralama geçmişini takip etmesini sağlamaktır.
🚀 Öne Çıkan Özellikler
•
Üye Ol & Giriş Yap: Supabase Auth kullanarak güvenli kullanıcı kaydı ve giriş sistemi.
•
Kitap Arşivi: Kütüphanedeki tüm kitapları 2'li grid yapısında, şık kartlarla listeleme.
•
Akıllı Arama: Kitap ismine göre anlık arama yapabilme.
•
Ödünç Alma (Borrow): Kitap detay sayfasından stok durumuna göre kitabı 5 günlüğüne ödünç alma.
•
Profilim: Kullanıcının aktif olarak elinde bulunan kitapları ve iade tarihini (kaç gün kaldığını) görmesi.
•
Kiralama Geçmişi: Daha önce alınan kitapların listesi ve iade durumlarının (Returned, Overdue vb.) takibi.
🛠️ Kullanılan Teknolojiler
Bu projede güncel Android geliştirme standartları uygulanmıştır:
•
Jetpack Compose: Tamamen deklaratif UI yapısı.
•
MVVM Mimari: Temiz kod ve mantıksal ayrım için ViewModel kullanımı.
•
Supabase: Veritabanı (PostgreSQL) ve kimlik doğrulama işlemleri için.
•
Kotlin Coroutines & Flow: Asenkron veri akışları ve performanslı çalışma.
•
Kotlinx Serialization: JSON veri dönüşümleri.
📂 Proje Yapısı
•
app/src/main/java/com/turkcell/libraryapp/ui/screen: Tüm ekranların (Login, Home, Profile vb.) tasarım kodları.
•
app/src/main/java/com/turkcell/libraryapp/ui/viewmodel: Ekranların mantıksal işlemlerini yöneten ViewModel sınıfları.
•
app/src/main/java/com/turkcell/libraryapp/data/repository: Supabase ile iletişim kuran veri sınıfları.
•
app/src/main/java/com/turkcell/libraryapp/data/model: Kitap ve Kiralama verilerini temsil eden Data Class'lar.
⚙️ Nasıl Çalıştırılır?
1.
Projeyi Android Studio ile açın.
2.
local.properties içine kendi Supabase URL ve Key bilgilerinizi ekleyin.
3.
Gradle senkronizasyonunu yapın ve uygulamayı bir emülatörde/cihazda çalıştırın.
