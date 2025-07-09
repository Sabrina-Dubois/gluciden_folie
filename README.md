# 🍰 🧁 Glucid'en Folie - Application de Recettes de Desserts Gourmands & Adaptés

Bienvenue sur Glucid'en Folie, une application web dédiée aux passionnés de pâtisserie soucieux de leur santé ou ayant des intolérances alimentaires (gluten, lactose, sucre, etc.).
Découvrez, partagez et sauvegardez vos recettes de desserts préférées — 100 % plaisir, 0 % frustration !

![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![Vuetify](https://img.shields.io/badge/Vuetify-1867C0?style=for-the-badge&logo=vuetify&logoColor=AEDDFF)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)

---

## 🔧 Technologies utilisées

### Frontend
- [Vue.js 3](https://vuejs.org/)
- [Vuetify 3](https://vuetifyjs.com/)
- [Pinia](https://pinia.vuejs.org/)
- [Vue Router](https://router.vuejs.org/)
- Vite (serveur de développement)

### Backend
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- Base de données : PostgreSQL 

---


## 📚 Sommaire
- Aperçu du projet
- Fonctionnalités
- Installation
- Utilisation
- Structure du projet
- Contact

---

## 🎯 Aperçu du projet
Glucid'en Folie a été pensé pour :
- Les personnes souffrant d’intolérances (gluten, lactose, etc.)
- Les personnes surveillant leur consommation de sucre
- Les gourmands souhaitant des alternatives plus saines

Grâce à une interface moderne, intuitive et responsive construite avec ![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)  et ![Vuetify](https://img.shields.io/badge/Vuetify-1867C0?style=for-the-badge&logo=vuetify&logoColor=AEDDFF) pour offrir une interface utilisateur élégante et réactive., vous pouvez explorer un catalogue de recettes, les filtrer selon vos envies, et même les noter ou commenter.

---

## ⚙️ Fonctionnalités

- Liste des recettes : Consultez toutes vos recettes en un coup d'œil
- Détails des recettes
- Recherche : Trouvez des recettes en fonction d'ingrédients spécifiques (a venir)
- Favoris : Marquez vos recettes préférées pour y accéder rapidement(a venir)
- Note : Notez les recettes sur votre expérience (venir)
- Commentaire : Ajoutez vos commentaires

---

## 🚀 Installation

### Prérequis
![Terminal](https://badgen.net/badge/icon/terminal?icon=terminal&label)
![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
(version 14 ou supérieure)

Vue CLI

### 📥 Étapes d'installation

### 1. Cloner le dépôt GitHub sur votre machine locale 
zsh
```
git clone https://github.com/Sabrina-Dubois/gluciden_folie.git
```
### 2.  Lancer le frontend (Vue.js)
- Accédez au répertoire du projet :
zsh
```
cd gluciden-folie/
```
- Installez les dépendances du projet :
zsh
```
npm install
```

- Lancez l'application en mode développement :
zsh
```
npm run dev
```
- Ouvrez votre navigateur et accédez à http://localhost:5173

### 3.  Lancer le backend (Spring Boot)
- Ouvrir le projet Java dans un IDE (ex: Eclipse ou IntelliJ)
- Lancer l’application Application.java (Spring Boot)
- Vérifiez que la base de données est bien configurée dans application.properties

---

## 🧁 Utilisation
Ajouter une recette : Naviguez vers "Ajouter une recette" via la barre de navigation, remplissez les informations nécessaires, puis enregistrez.
Modifier une recette : Sélectionnez une recette dans la liste, cliquez sur "Modifier", puis mettez à jour les informations.
Rechercher une recette : Utilisez la barre de recherche pour trouver des recettes par ingrédients ou noms de plats.

---

## Structure du projet
Le projet est organisé avec les dossiers typiques d'un projet Vue.js, incluant les composants, les vues, et les assets. 

Si vous avez besoin de plus de détails, n'hésitez pas à explorer directement le code.
```
gluciden-folie/
├── gluciden-folie-business/
│   └── index.html
├── gluciden-folie-database/
├── gluciden-folie-presentation/
│   └── public/
│   │   └── Images
│   └── src/
│   │   └── assets/
│   │   └── components/
│   │   └── i18n/
│   │   └── router/
│   │   │   └── index.js
│   ├── store/  
│   │   └── views/
│   ├── App.vue
│   ├── Main.js
│   └── package.json
├── .gitignore  
```

---

## 🧪 Environnement de développement
- Node.js ≥ 14
- JDK 17
- Vue CLI
- Base de données locale (PostgreSQL)

---

## Contact
Pour toute question ou suggestion, vous pouvez me contacter à ...





## 🎬 Démo

🧪 Une version de démonstration sera disponible prochainement ici :  
👉 [https://glucidenfolie.app](https://glucidenfolie.app) *(lien fictif, à remplacer)*

### 📸 Aperçu de l'application
> *(Ajoute une image quand tu veux, par ex. dans `/public/banner.png`)*

![Glucid'en Folie - Aperçu](public/banner.png)

---

## 🚀 Fonctionnalités principales

- 🔍 Recherche de recettes par nom, ingrédient ou catégorie
- 📝 Ajout, édition et suppression de recettes
- ❤️ Favoris
- ⭐ Notation et 💬 commentaires
- 🧑‍🤝‍🧑 Gestion des utilisateurs avec rôles (admin / utilisateur)
- ⚠️ Filtrage par intolérances alimentaires

---

## 🧩 Technologies utilisées

### Frontend
- [Vue.js 3](https://vuejs.org/)
- [Vuetify 3](https://vuetifyjs.com/)
- [Pinia](https://pinia.vuejs.org/)
- [Vue Router](https://router.vuejs.org/)
- Vite (serveur de développement)

### Backend
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- Base de données : PostgreSQL ou MySQL
- Envoi d’emails (validation de compte)

---

## 🛠️ Installation locale

### 1. Cloner le projet
```bash
git clone https://github.com/Sabrina-Dubois/gluciden_folie.git
cd gluciden_folie


📌 To-do (prochaines améliorations)
 Ajout de filtres dynamiques (gluten, lactose, etc.)

 Gestion des images (upload)

 Interface admin complète

 Historique des recettes vues

 Version mobile PWA



