# Appointment Management – Spring Boot Backend

Spring Boot backend εφαρμογή για διαχείριση ραντεβού και κατηγοριών.
Χρησιμοποιείται σε συνδυασμό με React frontend.

---

## Τεχνολογίες
- Java 21
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Security (JWT)
- MySQL
- Lombok
- Gradle

---

## Πώς τρέχει το project

1. Κάνε clone το repository
2. Δημιούργησε μια MySQL βάση (π.χ. `appointment_db`)
3. Συμπλήρωσε τα στοιχεία της βάσης στο:
   - `application.properties` ή
   - `application-dev.properties`
4. Άνοιξε το project στο IntelliJ IDEA
5. Τρέξε το Spring Boot application

Η εφαρμογή τρέχει στο:
http://localhost:8080

---

## Authentication (JWT)

### Login endpoint
POST /api/auth/login


### Παράδειγμα body
```json
{
  "username": "owner",
  "password": "1234"
}
Η απάντηση περιέχει JWT token που χρησιμοποιείται στα protected endpoints.
⚠️ Τα credentials αυτά χρησιμοποιούνται μόνο για demo / εκπαιδευτικούς σκοπούς
και δεν προορίζονται για παραγωγικό περιβάλλον.

##Ρόλοι & Δικαιώματα

Public (χωρίς login)
GET Categories
GET Appointments

OWNER (με JWT token)
Create / Update / Delete Categories
Create / Update / Delete Appointments

Swagger

http://localhost:8080/swagger-ui.html
Σημείωση:
Τα credentials της βάσης δεδομένων και το JWT secret δεν περιλαμβάνονται στο repository
και πρέπει να ρυθμιστούν τοπικά.

yaml
Copy code
