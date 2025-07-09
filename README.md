# 🍰 Glucid'en Folie - Application de Recettes de Desserts Gourmands & Adaptés

Bienvenue sur Glucid'en Folie, une application web dédiée aux passionnés de pâtisserie soucieux de leur santé ou ayant des intolérances alimentaires (gluten, lactose, sucre, etc.).
Découvrez, partagez et sauvegardez vos recettes de desserts préférées — 100 % plaisir, 0 % frustration !

---

## 🔧 Technologies utilisées

### Frontend
- [Vue.js](https://vuejs.org/)  
- [Vuetify](https://vuetifyjs.com/)  
- [Pinia](https://pinia.vuejs.org/) – pour la gestion d’état  
- Vue Router

### Backend
- Java 17  
- Spring Boot  
- Spring Data JPA  
- Base de données relationnelle (ex : PostgreSQL ou MySQL)

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
├── node_modules/            # Dépendances installées via npm
├── public/                  # Fichiers statiques accessibles publiquement
│   ├── favicon.ico          # Icône de la page
│   └── index.html           # Page HTML principale
├── src/                     # Répertoire principal pour le code source
│   ├── assets/              # Fichiers statiques comme les images, icônes, etc.
│   │   └── images/          # Images utilisées dans le projet (ex : logo)
│   ├── components/          # Composants Vue réutilisables
│   │   ├── Header.vue       # Composant de la barre de navigation
│   │   ├── Footer.vue       # Composant de pied de page
│   │   └── RecipeCard.vue   # Composant pour afficher une carte de recette
│   ├── views/               # Vues (pages) principales de l'application
│   │   ├── RecipesList.vue  # Page listant toutes les recettes
│   │   ├── CreateRecipe.vue # Page pour créer une nouvelle recette
│   │   └── UpdateRecipe.vue # Page pour modifier une recette existante
│   ├── router/              # Configuration du routeur Vue
│   │   └── index.js         # Définition des routes de l'application
│   ├── store/               # Gestion de l'état global avec Vuex
│   │   └── index.js         # Configuration et modules Vuex
│   ├── App.vue              # Composant racine de l'application
│   └── main.js              # Point d'entrée principal de l'application
├── .gitignore               # Fichiers et dossiers à
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

