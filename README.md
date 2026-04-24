# 📚 LibraryApp

> Bu proje, **Turkcell Geleceği Yazanlar - Android Geliştirme Eğitimi (GYGY5)** kapsamında eğitim amaçlı geliştirilmiş bir kütüphane uygulamasıdır.

---

## 🎯 Proje Hakkında

Bu uygulama, Android geliştirme eğitimi sürecinde **Jetpack Compose** ve modern Android mimarisi konularını öğrenmek amacıyla yazılmıştır. Eğitim sürecinde kazanılan becerileri pekiştirmek için geliştirilmiş örnek bir projedir.

---

## 🛠️ Kullanılan Teknolojiler

- **Kotlin** - Ana programlama dili
- **Jetpack Compose** - Modern UI toolkit
- **Navigation Component** - Ekranlar arası geçiş
- **ViewModel** - UI state yönetimi
- **StateFlow** - Reaktif veri akışı
- **Material Design 3** - UI bileşenleri

---

## 📁 Proje Yapısı

```
cocom.turkcell.libraryapp/
├── data/
│   ├── model/
│   └── repository/
│       └── AuthRepository.kt
├── ui/
│   ├── navigation/
│   │   ├── NavGraph.kt
│   │   └── Screen.kt
│   ├── screen/
│   │   ├── LoginScreen.kt
│   │   └── RegisterScreen.kt
│   └── viewmodel/
│       └── AuthViewModel.kt
└── MainActivity.kt
```

---

## 🚀 Öğrenilen Konular

- Jetpack Compose ile UI geliştirme
- `OutlinedTextField`, `Button`, `LaunchedEffect` gibi Compose bileşenleri
- `ViewModel` ve `StateFlow` ile state yönetimi
- `NavController` ile sayfa navigasyonu
- `sealed class` ile durum (state) modelleme (`AuthState`)
- Kullanıcı girişi validasyonu

---

## ⚙️ Kurulum

1. Repoyu klonla:
```bash
git clone https://github.com/Ozansis/turkcell-gygy5-LibraryApp.git
```

2. Android Studio'da aç

3. Gradle sync yaptıktan sonra çalıştır

---

## 📝 Not

Bu proje **Turkcell GYGY5 eğitimi** kapsamında yazılmıştır. Kod, eğitmen rehberliğinde ders ortamında geliştirilmiştir. 

---

## 📖 Eğitim Programı

**Turkcell Geleceği Yazanlar** — Android Uygulama Geliştirme Bootcamp  
