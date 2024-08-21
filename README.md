# Glucid'en Folie - Application de Recettes de Desserts
Bienvenue sur Glucid'en Folie, une application web pour vous faire partager des recettes de desserts spécifiques à certaines intolérences alimentaires.

Cette application est construite avec Vue.js et Vuetify.
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![Vuetify](https://img.shields.io/badge/Vuetify-1867C0?style=for-the-badge&logo=vuetify&logoColor=AEDDFF)


## 1) Table des matières
- Aperçu du projet
- Fonctionnalités
- Installation
- Utilisation
- Structure du projet
- Contact

## 2) Aperçu du projet
Glucid'en Folie est conçu pour les amateurs de desserts qui ont des intolérences alimentaires ou pour les personnes gourmandes faisant attention à leur ligne.
Il vous permet de rechercher des recettes par ingrédients, par nom ou catégories.
L'application utilise une architecture front-end moderne avec ![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![Vuetify](https://img.shields.io/badge/Vuetify-1867C0?style=for-the-badge&logo=vuetify&logoColor=AEDDFF) pour offrir une interface utilisateur élégante et réactive.


## 3) Fonctionnalités
- Liste des recettes : Consultez toutes vos recettes en un coup d'œil
- Recherche : Trouvez des recettes en fonction d'ingrédients spécifiques
- Favoris : Marquez vos recettes préférées pour y accéder rapidement
- Note : Notez les recettes sur votre expérience
- Commentaire : Ajoutez vos commentaires


## 4) Installation

### Prérequis
![Terminal](https://badgen.net/badge/icon/terminal?icon=terminal&label)
![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
(version 14 ou supérieure)

Vue CLI

### Étapes d'installation
- Clonez le dépôt GitHub sur votre machine locale :
zsh
```
git clone https://github.com/Sabrina-Dubois/gluciden_folie.git
```

- Accédez au répertoire du projet :
zsh
```
cd gluciden-folie
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
- Ouvrez votre navigateur et accédez à http://localhost:8080

## 5) Utilisation
Ajouter une recette : Naviguez vers "Ajouter une recette" via la barre de navigation, remplissez les informations nécessaires, puis enregistrez.
Modifier une recette : Sélectionnez une recette dans la liste, cliquez sur "Modifier", puis mettez à jour les informations.
Rechercher une recette : Utilisez la barre de recherche pour trouver des recettes par ingrédients ou noms de plats.


## 6) Structure du projet
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

## 7) Contact
Pour toute question ou suggestion, vous pouvez me contacter à ...

