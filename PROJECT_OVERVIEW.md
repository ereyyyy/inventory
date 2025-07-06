# Inventory Service - Proje Dokümantasyonu

## Genel Bakış
Bu proje, ürünlerin stok bilgisini yöneten bir **Inventory Service** mikroservisidir. Temel amacı, ürünlerin envanter kayıtlarını tutmak ve stok artırma, azaltma, sorgulama işlemlerini sağlamaktır.

---

## Mimarideki Yeri
Inventory Service, bir e-ticaret veya stok yönetim sisteminin temel mikroservislerinden biridir. Diğer mikroservislerle (ör. Product Service, Shopping Cart Service) entegre çalışabilir.

---

## Katmanlar ve Yapı

### 1. Controller Katmanı
- **InventoryController.java**
  - REST API uç noktalarını barındırır.
  - `/api` altında 4 ana endpoint:
    - `/create-inventory` (POST): Yeni ürün kaydı açar.
    - `/increase-inventory-count` (POST): Ürün stokunu 1 artırır.
    - `/decrease-inventory-count` (POST): Ürün stokunu 1 azaltır.
    - `/get-inventory-count` (POST): Ürün stokunu döner.
  - Tüm endpointler body olarak düz metin (ör: konsol) alır.

### 2. Service Katmanı
- **ProductService.java** (interface)
- **ProductServiceImpl.java** (implementasyon)
  - Controller'dan gelen isteklerin iş mantığını yürütür.
  - Ürün adı ile veritabanında arama yapar, stok artırma/azaltma/sorgulama işlemlerini gerçekleştirir.
  - Ürün bulunamazsa veya stok yetersizse hata fırlatır.

### 3. Repository Katmanı
- **InventoryRepository.java**
  - JPA ile veritabanı işlemlerini yapar.
  - `findByProductName` metodu ile ürün adına göre arama yapar (case sensitive).
  - CRUD işlemleri için JpaRepository'den kalıtım alır.

### 4. Entity Katmanı
- **InventoryEntity.java**
  - Veritabanındaki `inventory` tablosunu temsil eder.
  - Alanlar:
    - `id`: Otomatik artan birincil anahtar.
    - `productName`: Ürün adı.
    - `productCount`: Stok adedi.

### 5. Konfigürasyon
- **application.properties**
  - Uygulama adı, port (8086), veritabanı bağlantısı (PostgreSQL), JPA ayarları burada tanımlı.

### 6. Bağımlılıklar
- Spring Boot (Web, Data JPA)
- PostgreSQL
- Lombok
- MapStruct
- Springdoc OpenAPI (Swagger)

---

## Detaylı İş Akışı ve Senaryolar

### 1. Ürün Oluşturma (Create Inventory)
**Senaryo:** Yeni bir ürün sisteme eklenmek isteniyor.

**Adımlar:**
1. Kullanıcı veya başka bir servis `/api/create-inventory` endpointine ürün adını (ör: `konsol`) body olarak gönderir.
2. Controller, bu isteği Service katmanına iletir.
3. Service, yeni bir `InventoryEntity` nesnesi oluşturur ve `productCount` alanını 0 olarak ayarlar.
4. Repository, bu nesneyi veritabanına kaydeder.
5. Başarılıysa 200 OK döner.

**Veritabanı Değişimi:**
- Yeni bir satır eklenir: `{id: 1, productName: 'konsol', productCount: 0}`

---

### 2. Stok Artırma (Increase Inventory Count)
**Senaryo:** Var olan bir ürünün stoğu artırılmak isteniyor.

**Adımlar:**
1. `/api/increase-inventory-count` endpointine ürün adı (ör: `konsol`) gönderilir.
2. Controller, isteği Service katmanına iletir.
3. Service, ürün adını veritabanında arar.
   - Eğer ürün yoksa hata fırlatır.
4. Ürün bulunursa, mevcut `productCount` değeri 1 artırılır ve tekrar kaydedilir.
5. Başarılıysa 200 OK döner.

**Veritabanı Değişimi:**
- `{id: 1, productName: 'konsol', productCount: 1}`

**Hata Durumu:**
- Ürün yoksa: 400 Bad Request, "Product not found: konsol"

---

### 3. Stok Azaltma (Decrease Inventory Count)
**Senaryo:** Var olan bir ürünün stoğu azaltılmak isteniyor.

**Adımlar:**
1. `/api/decrease-inventory-count` endpointine ürün adı gönderilir.
2. Controller, isteği Service katmanına iletir.
3. Service, ürün adını veritabanında arar.
   - Eğer ürün yoksa hata fırlatır.
4. Ürün bulunursa, mevcut `productCount` değeri 1 azaltılır.
   - Eğer stok zaten 0 ise hata fırlatır.
5. Güncellenen değer kaydedilir.
6. Başarılıysa 200 OK döner.

**Veritabanı Değişimi:**
- `{id: 1, productName: 'konsol', productCount: 0}`

**Hata Durumları:**
- Ürün yoksa: 400 Bad Request, "Product not found: konsol"
- Stok yetersizse: 400 Bad Request, "Insufficient stock for product: konsol"

---

### 4. Stok Sorgulama (Get Inventory Count)
**Senaryo:** Bir ürünün mevcut stok miktarı öğrenilmek isteniyor.

**Adımlar:**
1. `/api/get-inventory-count` endpointine ürün adı gönderilir.
2. Controller, isteği Service katmanına iletir.
3. Service, ürün adını veritabanında arar.
   - Eğer ürün yoksa hata fırlatır.
4. Ürün bulunursa, mevcut `productCount` değeri döner.
5. Başarılıysa 200 OK ve stok miktarı döner.

**Veritabanı Değişimi:**
- Değişiklik olmaz, sadece okuma işlemi yapılır.

**Hata Durumu:**
- Ürün yoksa: 400 Bad Request, "Product not found: konsol"

---

### 5. Hata Yönetimi ve Loglama
- Tüm hata durumlarında (ürün yok, stok yetersiz) açıklayıcı mesaj ve 400 Bad Request döner.
- Service katmanında exception fırlatılır, Controller'da standart HTTP cevabı döner.
- Gerekirse loglama ile hata ve işlem geçmişi tutulabilir.

---

### 6. Servisler Arası Entegrasyon (Olası Senaryo)
- **Product Service** yeni bir ürün oluşturduğunda, Inventory Service'e otomatik olarak `/create-inventory` çağrısı yapabilir.
- **Shopping Cart Service** bir satış işlemi gerçekleştiğinde, Inventory Service'e `/decrease-inventory-count` çağrısı yaparak stok azaltabilir.
- Diğer servisler, stok kontrolü için `/get-inventory-count` endpointini kullanabilir.

---

## Kullanım
- API'ye düz metin (ör: konsol) gönderilerek çalışır.
- Swagger arayüzü ile kolayca test edilebilir.

---

## Geliştirme Önerileri
- Sorguları case-insensitive yapmak.
- Stok hareket geçmişi (audit) eklemek.
- Minimum stok uyarı sistemi.
- Toplu stok güncelleme.
- Exception handler ile daha standart hata yönetimi.
- Kullanıcı kimliği ile işlem loglama.

---

## Özet
Bu mikroservis, ürünlerin stok bilgisini yönetmek için sade, anlaşılır ve katmanlı bir mimariyle geliştirilmiş; Spring Boot ve PostgreSQL kullanan bir Inventory Service'tir. Dışarıya REST API olarak hizmet verir ve temel stok yönetimi işlevlerini yerine getirir. 