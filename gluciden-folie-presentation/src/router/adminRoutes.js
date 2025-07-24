export default [
  {
    path: "/",
    name: "home",
    component: () => import("../views/Home.vue"),
  },
  {
    path: "/creer-recette",
    name: "createRecipe",
    component: () => import("../views/CreateRecipe.vue"),
    meta: { requiresAdmin: true },
  },
  {
    path: "/modifier-recette/:id",
    name: "updateRecipe",
    component: () => import("@/views/UpdateRecipe.vue"),
    meta: { requiresAdmin: true },
  },
  {
    path: "/liste-recettes",
    name: "recipesList",
    component: () => import("@/views/RecipesList.vue"),
  },
  {
    path: "/recipes/:id",
    name: "recipeDetails",
    component: () => import("@/views/RecipeDetails.vue"),
    props: true,
  },
  {
    path: "/creer-categorie",
    name: "createCategory",
    component: () => import("../views/CreateCategory.vue"),
    meta: { requiresAdmin: true },
  },
  {
    path: "/liste-catégories",
    name: "categoriesList",
    component: () => import("../views/CategoriesList.vue"),
  },
  {
    path: "/modifier-catégorie/:id",
    name: "updateCategory",
    component: () => import("@/views/UpdateCategory.vue"),
    meta: { requiresAdmin: true },
  },
  {
    path: "/unauthorized",
    name: "unauthorized",
    component: () => import("../views/Unauthorized.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../views/Authentification.vue"),
  },
  {
    path: "/authentification/:action",
    name: "authentification",
    component: () => import("../views/Authentification.vue"),
  },
];
