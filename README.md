DOKUMENTACJA

🏨 HotelBooking – system rezerwacji hotelowych
HotelBooking to aplikacja webowa napisana w języku Java z wykorzystaniem frameworka Spring Boot, która umożliwia użytkownikom dokonywanie rezerwacji pokojów hotelowych. System wspiera dwa typy użytkowników: administratorów i zwykłych użytkowników, z odrębnymi uprawnieniami i widokami po zalogowaniu.

🎯 Główne funkcjonalności:

✅ Logowanie z weryfikacją ról (ADMIN, USER) przy użyciu Spring Security

🛏️ Rezerwacje pokoi z podziałem na terminy, pokoje i przypisanych użytkowników

👨‍💼 Administrator ma dostęp do wszystkich danych – może przeglądać i zarządzać wszystkimi rezerwacjami

🙋 Zwykły użytkownik widzi tylko swoje rezerwacje

📜 Formularz logowania oraz dynamiczne przekierowanie na odpowiedni panel (admin/user)

🔄 Interfejs REST API udostępniony poprzez kontrolery

🧪 Swagger UI – graficzna dokumentacja i testowanie endpointów

💾 Baza danych PostgreSQL zarządzana przez Liquibase, umożliwiający kontrolowane migracje danych

🧱 Użyte technologie:

Spring Boot – szybkie tworzenie aplikacji backendowej

Spring Security – autoryzacja i kontrola ról użytkowników

Spring Data JPA (Hibernate) – komunikacja z bazą danych

PostgreSQL – trwałe przechowywanie danych

Liquibase – migracje danych bez utraty informacji

Swagger (Springdoc OpenAPI) – interaktywna dokumentacja REST API

Thymeleaf – prosty silnik szablonów HTML (login + dashboard)

Maven – zarządzanie zależnościami i budowanie aplikacji

Docker / Docker Compose – konteneryzacja bazy danych i aplikacji

👩‍💻 Użytkownicy testowi
Administrator
username: admin
password: admin

Użytkownik
username: user4
password: pass




Szczegóły:

1. Projekt postawiony na Dockerze

![image](https://github.com/user-attachments/assets/85e1b024-bbef-4e91-8f08-870748c61166)

2. Struktura Maven wraz z strukturą projektu

![image](https://github.com/user-attachments/assets/d38cadcb-3d7e-44ef-a35a-e3fdc3c6e7b6)

![image](https://github.com/user-attachments/assets/eb617e83-8070-4463-b02d-7b12fff226e9)


3. Użycie REST Controller, RequestMapping:

![image](https://github.com/user-attachments/assets/5ff0c905-979a-45d0-bae2-d0b5af9a1960)


4. Użycie Spring Security do zarządzania użytkownikami i uprawnieniami
(admin po zalogowaniu ma dostęp do wszystkich żądań)

![image](https://github.com/user-attachments/assets/088c4a5d-9907-4b5f-99e3-82eb67dd5825)


5. Dodatkowo templatki html dla logowania, dashboardu usera i admina osobno
(wyświetlanie rezerwacji wszystkich u admina, danego usera u usera)

![image](https://github.com/user-attachments/assets/76f0792f-b1bb-479c-b8aa-93f2d7ba5de3)

![image](https://github.com/user-attachments/assets/abe4483a-c8b7-4c2b-b432-e25bed533345)

![image](https://github.com/user-attachments/assets/283978a4-4b6e-41d2-8837-0b2d61cd240d)



6. Użycie Swagger UI (potrzebne konkretne uprawnienia)

![image](https://github.com/user-attachments/assets/7ce7836d-3261-4aef-b5ac-f98074bb148d)


7. Mapowanie obiektowo-relacyjne - Hibernate oraz baza danych PostgreSQL

![image](https://github.com/user-attachments/assets/632cbb10-26dc-4a4a-b222-7d06d2ffdb96)
![image](https://github.com/user-attachments/assets/7bc4dd5f-d56a-4407-b3f7-a4dd46e3d0d4)
![image](https://github.com/user-attachments/assets/a9d1fbc9-7fa5-4931-b6de-27666bd5d123)


8. Diagram ERD bazy danych:

![image](https://github.com/user-attachments/assets/b1221456-4217-41d7-8798-7c5cf924d306)


9. Aplikacja obsługuje Liquibase do migracji bazy danych:

![image](https://github.com/user-attachments/assets/1280c474-52e1-4457-a7f3-4abc562518a9)


10. Testowanie (JUnit):

![image](https://github.com/user-attachments/assets/e5a4170d-cdfa-4a2b-bdb9-297c4201747f)


11. Użyte wzorce:

- Warstwa serwisowa (Service Layer)
Odzielenie logiki biznesowej od logiki kontrolera (obsługi http)

- MVC (Model-View-Controller)
W warstwie frontendowe stosuję formularz logowania gdzie:
Model = dane użytkownika
View = HTML (pliki login.html, dashboard.html)
Controller = LoginController
