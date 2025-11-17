# Eventify 

- Gestion d'Événements Sécurisée
📋 Description du Projet
Eventify est une application de gestion d'événements sécurisée développée avec Spring Boot et Spring Security. Elle permet aux utilisateurs de s'inscrire à des événements, aux organisateurs de créer et gérer leurs événements, et aux administrateurs de superviser l'ensemble du système.
🎯 Fonctionnalités Principales


Gestion des Utilisateurs : Inscription, authentification et gestion des profils
Gestion des Événements : Création, modification, suppression et consultation d'événements
Système d'Inscription : Les utilisateurs peuvent s'inscrire aux événements disponibles
Administration : Gestion des utilisateurs et des événements par les administrateurs
Sécurité Robuste : Authentification Basic avec gestion des rôles et permissions

# 🔐 Architecture de Sécurité
Rôles Disponibles

ROLE_USER : Utilisateur standard (rôle par défaut)
ROLE_ORGANIZER : Organisateur d'événements
ROLE_ADMIN : Administrateur système

Authentification

Type : Basic Authentication
Encodage : BCryptPasswordEncoder
Provider : CustomAuthenticationProvider avec UserDetailsService personnalisé
Session : Stateless (SessionCreationPolicy.STATELESS)
CSRF : Désactivé (API REST stateless)

Configuration des Accès
/api/public/**     → Accessible sans authentification
/api/user/**       → Requiert ROLE_USER
/api/organizer/**  → Requiert ROLE_ORGANIZER
/api/admin/**      → Requiert ROLE_ADMIN

# 🚀 Installation et Lancement
Prérequis

Java 17 ou supérieur
Maven 3.8+
PostgreSQL
Git

Configuration de la Base de Données

Créez une base de données MySQL :

sqlCREATE DATABASE eventify_db;

Configurez les paramètres dans application.properties :

propertiesspring.datasource.url=jdbc:mysql://localhost:3306/eventify_db
spring.datasource.username=votre_username
spring.datasource.password=votre_password
spring.jpa.hibernate.ddl-auto=update
Installation
bash# 

Cloner le repository
git clone https://github.com/charafeddine-web/eventify.git
cd eventify

# Installer les dépendances
mvn clean install

# Lancer l'application
mvn spring-boot:run
L'application sera accessible sur http://localhost:8080
📡 Endpoints API
🌐 Public (Sans Authentification)
Inscription d'un Utilisateur
httpPOST /api/public/users
Content-Type: application/json

{
"name": "John Doe",
"email": "john@example.com",
"password": "SecurePass123"
}

Liste des Événements Publics
httpGET /api/public/events

👤 USER (Authentification Requise)

Consulter son Profil
httpGET /api/user/profile
Authorization: Basic base64(email:password)
S'inscrire à un Événement
httpPOST /api/user/events/{id}/register
Authorization: Basic base64(email:password)
Consulter ses Inscriptions
httpGET /api/user/registrations
Authorization: Basic base64(email:password)

🎪 ORGANIZER (Rôle Organisateur)
Créer un Événement
httpPOST /api/organizer/events
Authorization: Basic base64(email:password)
Content-Type: application/json

{
"title": "Conférence Tech 2024",
"description": "Une conférence sur les nouvelles technologies",
"location": "Paris",
"dateTime": "2024-12-15T14:00:00",
"capacity": 100
}
Modifier un Événement
httpPUT /api/organizer/events/{id}
Authorization: Basic base64(email:password)
Content-Type: application/json

{
"title": "Conférence Tech 2024 - Édition Spéciale",
"capacity": 150
}
Supprimer un Événement
httpDELETE /api/organizer/events/{id}
Authorization: Basic base64(email:password)

🔧 ADMIN (Rôle Administrateur)
Lister tous les Utilisateurs
httpGET /api/admin/users
Authorization: Basic base64(email:password)
Modifier le Rôle d'un Utilisateur
httpPUT /api/admin/users/{id}/role
Authorization: Basic base64(email:password)
Content-Type: application/json

{
"role": "ROLE_ORGANIZER"
}
Supprimer un Événement
httpDELETE /api/admin/events/{id}
Authorization: Basic base64(email:password)
```

## 🏗️ Architecture du Projet
```
src/main/java/com/eventify/
├── config/
│   ├── SecurityConfig.java              # Configuration Spring Security
│   └── PasswordEncoderConfig.java       # Configuration BCrypt
├── security/
│   ├── CustomAuthenticationProvider.java
│   ├── CustomUserDetailsService.java
│   ├── CustomAuthenticationEntryPoint.java
│   └── CustomAccessDeniedHandler.java
├── controller/
│   ├── PublicController.java
│   ├── UserController.java
│   ├── OrganizerController.java
│   └── AdminController.java
├── service/
│   ├── UserService.java
│   ├── EventService.java
│   └── RegistrationService.java
├── repository/
│   ├── UserRepository.java
│   ├── EventRepository.java
│   └── RegistrationRepository.java
├── model/
│   ├── User.java
│   ├── Event.java
│   └── Registration.java
├── dto/
│   ├── UserDTO.java
│   ├── EventDTO.java
│   └── ErrorResponseDTO.java
└── exception/
├── GlobalExceptionHandler.java
├── UsernameAlreadyExistsException.java
├── EventNotFoundException.java
└── UnauthorizedActionException.java
🗄️ Modèle de Données
User
java- id: Long (PK)
- name: String
- email: String (unique)
- password: String (encodé)
- role: String (ROLE_USER, ROLE_ORGANIZER, ROLE_ADMIN)
  Event
  java- id: Long (PK)
- title: String
- description: String
- location: String
- dateTime: LocalDateTime
- capacity: Integer
- organizerId: Long (FK → User)
  Registration
  java- id: Long (PK)
- userId: Long (FK → User)
- eventId: Long (FK → Event)
- registeredAt: LocalDateTime
- status: String (CONFIRMED, CANCELLED)
  ⚠️ Gestion des Erreurs
  L'application gère les erreurs de manière centralisée avec des réponses standardisées :
  Format de Réponse d'Erreur
  json{
  "timestamp": "2024-11-17T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Accès refusé : vous n'avez pas les permissions nécessaires",
  "path": "/api/admin/users"
  }
  Codes HTTP Utilisés

200 : Succès
201 : Création réussie
400 : Requête invalide
401 : Non authentifié (AuthenticationEntryPoint)
403 : Accès refusé (AccessDeniedHandler)
404 : Ressource introuvable
409 : Conflit (email déjà existant)
500 : Erreur serveur

🧪 Tests
Profil Test
Un profil Spring test est disponible pour bypasser l'encodage du mot de passe lors des tests :
properties# application-test.properties
spring.profiles.active=test
Lancer les Tests
bashmvn test
```

### Scénarios de Test Recommandés
1. ✅ Inscription d'un utilisateur avec rôle USER par défaut
2. ✅ Authentification avec credentials valides/invalides
3. ✅ Accès refusé (403) pour un USER tentant d'accéder à /api/admin/**
4. ✅ Création d'événement par un ORGANIZER
5. ✅ Modification de rôle par un ADMIN
6. ✅ Inscription à un événement par un USER
7. ❌ Suppression d'événement par un USER (403)
8. ❌ Accès à un endpoint protégé sans authentification (401)

## 🔑 Données de Test

Après le lancement initial, vous pouvez créer des utilisateurs de test :

### Admin
```
Email: admin@eventify.com
Password: Admin123!
Role: ROLE_ADMIN
```

### Organizer
```
Email: organizer@eventify.com
Password: Organizer123!
Role: ROLE_ORGANIZER
```

### User
```
Email: user@eventify.com
Password: User123!
Role: ROLE_USER
🛠️ Technologies Utilisées

Backend : Spring Boot 3.x
Sécurité : Spring Security 6.x
Base de données : PostgreSQL
ORM : Spring Data JPA (Hibernate)
Build : Maven
Documentation : SpringDoc OpenAPI (optionnel)

👥 Contributeurs

Développeur 1 : charaf edidne
Développeur 2 : khawla

📝 Licence
Ce projet est développé dans un cadre pédagogique.
📞 Support
Pour toute question ou problème :

Créez une issue sur GitHub
Contactez l'équipe de développement


Date de création : 17/11/2024
Version : 1.0.0
