export default [
  {
    path: "/",
    name: "home",
    component: () => import("../views/Home.vue"),
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
    path: "/liste-catégories",
    name: "categoriesList",
    component: () => import("../views/CategoriesList.vue"),
  },
  {
    path: "/unauthorized",
    name: "unauthorized",
    component: () => import("../views/Unauthorized.vue"),
  },
];
