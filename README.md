DigitalFatigueDiary – Dijital Yorgunluk & Ekran Etkisi Günlüğü

DigitalFatigueDiary, kullanıcıların günlük ekran kullanım süreleri ile dijital yorgunluk, odaklanma ve ruh hali arasındaki ilişkiyi takip etmelerini sağlayan bir Android uygulamasıdır.
Uygulama, bireylerin dijital alışkanlıklarını fark etmelerine ve ekran kullanımının günlük ruhsal/fiziksel etkilerini analiz etmelerine yardımcı olmayı amaçlar.

-Özellikler
Günlük Kayıt Sistemi

Kullanıcılar günlük olarak;

Ekran süresi (dakika),

Yorgunluk seviyesi (1–5),

Odak seviyesi (1–5),

Ruh hali,

Bildirim yoğunluğu

gibi bilgileri kaydedebilir.

Günlük Kayıt Kontrolü

Aynı güne birden fazla kayıt eklenmesi engellenir.

Gelecek tarihli kayıt girişine izin verilmez.

Son 7 Gün Analizi

Son 7 güne ait ortalama ekran süresi,

ortalama yorgunluk ve odak seviyesi hesaplanır.

Kaç günlük veri üzerinden hesap yapıldığı kullanıcıya açıkça gösterilir.

Kayıtları Görüntüleme ve Silme

Tüm geçmiş kayıtlar liste halinde görüntülenir.

Uzun basma ile kayıt silme işlemi yapılabilir.

Basit ve Anlaşılır Arayüz

Kullanıcıyı yormayan sade tasarım

Günlük kullanım için hızlı ve pratik ekranlar

🛠 Kullanılan Teknolojiler

Dil: Java

Platform: Android Studio

Veritabanı: SQLite (SQLiteOpenHelper, Cursor)

Kullanıcı Arayüzü: Material Design, LinearLayout, AppCompat

Minimum SDK: API 24 (Android 7.0)

-Proje Yapısı
MainActivity.java

Uygulamanın ana ekranıdır.

Bugün için kayıt olup olmadığı kontrol edilir.

Günlük özet bilgileri ve son 7 gün ortalamaları görüntülenir.

Kayıt ekleme ve kayıtlar sayfasına geçiş buradan sağlanır.

KayitEkleActivity.java

Kullanıcının günlük verilerini girdiği ekrandır.

Günlük ekran süresi, yorgunluk, odak, ruh hali ve bildirim bilgileri alınır.

Girilen veriler SQLite veritabanına kaydedilir.

KayitlarActivity.java

Tüm geçmiş kayıtların listelendiği ekrandır.

Kayıtlar tarih sırasına göre görüntülenir.

Uzun basma ile kayıt silme işlemi yapılır.

DatabaseHelper.java

SQLite veritabanının oluşturulması

Günlük kayıt ekleme

Kayıt kontrolü (aynı gün / gelecek tarih)

Son 7 gün ortalama hesaplamaları
gibi tüm veritabanı işlemlerinin yönetildiği sınıftır.

<img width="364" height="817" alt="Ekran görüntüsü 2026-01-11 201252" src="https://github.com/user-attachments/assets/1b7fa10d-3d1e-4562-a60d-4a8d518fcd18" />
<img width="362" height="811" alt="Ekran görüntüsü 2026-01-11 201328" src="https://github.com/user-attachments/assets/6bd0063f-fd4c-4059-a13c-086d77ffd4a5" />
<img width="362" height="814" alt="Ekran görüntüsü 2026-01-11 201340" src="https://github.com/user-attachments/assets/d491c6b1-2803-4ab3-a99e-f2e19090cb77" />
<img width="1913" height="943" alt="Ekran görüntüsü 2026-01-11 205437" src="https://github.com/user-attachments/assets/ae8a613f-c798-4ef0-866a-dfec24ceff9e" />
<img width="1916" height="948" alt="Ekran görüntüsü 2026-01-11 210833" src="https://github.com/user-attachments/assets/20b46966-2aa6-44d0-9992-a1ba0430c807" />










