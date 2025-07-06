# Inventory Service API Documentation

## Genel Bilgiler
- **Base URL:** `http://localhost:8086/api`
- **Content-Type:** `text/plain`
- **Port:** 8086

---

## API Endpoints

### 1. Create Inventory
**POST** `/create-inventory`

**Açıklama:**
Yeni bir ürün için envanter kaydı oluşturur. Ürün adı ile birlikte stok 0 olarak başlatılır.

**Request Body:**
```
konsol
```

**Response:**
```
200 OK
```

---

### 2. Increase Inventory Count
**POST** `/increase-inventory-count`

**Açıklama:**
Belirtilen ürünün stok adedini 1 artırır.

**Request Body:**
```
konsol
```

**Response:**
```
200 OK
```

**Hata Durumu:**
```
400 Bad Request
Product not found: konsol
```

---

### 3. Decrease Inventory Count
**POST** `/decrease-inventory-count`

**Açıklama:**
Belirtilen ürünün stok adedini 1 azaltır. Stok 0'ın altına inemez.

**Request Body:**
```
konsol
```

**Response:**
```
200 OK
```

**Hata Durumları:**
```
400 Bad Request
Product not found: konsol
```
veya
```
400 Bad Request
Insufficient stock for product: konsol
```

---

### 4. Get Inventory Count
**POST** `/get-inventory-count`

**Açıklama:**
Belirtilen ürünün stok adedini döner.

**Request Body:**
```
konsol
```

**Response:**
```
200 OK
0
```

**Hata Durumu:**
```
400 Bad Request
Product not found: konsol
```

---

## Notlar
- Tüm endpointler `text/plain` body ile çalışır (ör: konsol).
- Ürün adı büyük/küçük harf ve boşluklara duyarlı olabilir.
- Başarılı işlemlerde 200 OK, hata durumunda 400 Bad Request ve açıklama mesajı döner. 