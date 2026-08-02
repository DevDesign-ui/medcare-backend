# MEDCARE Backend

Backend Spring Boot pour la gestion d'un dispensaire medical.

## Technologies

- Java 21
- Spring Boot 3.3.2
- Spring Web, Spring Data JPA, Hibernate
- Spring Security + JWT
- MySQL 8
- Swagger / OpenAPI (springdoc)
- Maven

## Demarrage

### Prerequis

- Java 21
- Maven 3.9+
- MySQL 8

### Configuration MySQL

La configuration se trouve dans `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medcare_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

### Compilation et lancement

```bash
mvn clean package
java -jar target/medcare-backend-1.0.0.jar
```

Le serveur demarre sur le port **8080**.

## Utilisateurs par defaut

Des utilisateurs sont crees automatiquement au demarrage :

| Role        | Email                    | Mot de passe |
|-------------|--------------------------|--------------|
| ADMIN       | admin@medcare.com        | admin123     |
| MEDECIN     | medecin@medcare.com      | medecin123    |
| SECRETAIRE  | secretaire@medcare.com   | secret123    |
| PHARMACIEN  | pharmacien@medcare.com   | pharma123    |

## API REST

Base URL : `http://localhost:8080/api`

### Authentification

| Methode | Endpoint           | Description              |
|---------|--------------------|--------------------------|
| POST    | /api/auth/register | Creer un utilisateur     |
| POST    | /api/auth/login    | Connexion (retourne JWT) |
| GET     | /api/auth/users    | Lister les utilisateurs   |

### Endpoints CRUD

- `/api/patients` - Gestion des patients
- `/api/consultations` - Gestion des consultations
- `/api/rendezvous` - Gestion des rendez-vous
- `/api/medicaments` - Gestion des medicaments
- `/api/stocks` - Gestion du stock
- `/api/factures` - Gestion des factures
- `/api/personnels` - Gestion du personnel

### Securite par role

| Role        | Acces                                                            |
|-------------|------------------------------------------------------------------|
| ADMIN       | Acces complet a toutes les ressources                            |
| MEDECIN     | Patients, Consultations, Rendez-vous                             |
| SECRETAIRE  | Patients, Rendez-vous, Factures                                 |
| PHARMACIEN  | Medicaments, Stock                                               |

### Swagger

Interface Swagger UI : `http://localhost:8080/swagger-ui.html`

### CORS

Configure pour autoriser le frontend Angular sur `http://localhost:4200`.

## Tests

```bash
mvn test
```

Les tests couvrent : authentification JWT, creation d'utilisateur, CRUD Patient, securite des roles.

## Architecture

```
src/main/java/com/medcare
├── controller       - Controleurs REST
├── service          - Interfaces de service
│   └── impl         - Implementations de service
├── repository       - Repositories JPA
├── entity           - Entites JPA
├── dto              - Objets de transfert de donnees
├── mapper           - Mappeurs Entity <-> DTO
├── security         - JWT, UserDetailsService, filtres
├── config           - Configuration Spring Security, Swagger, CORS
├── exception        - Gestion globale des erreurs
└── util             - Utilitaires
```
