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
    path: "/unauthorized",
    name: "unauthorized",
    component: () => import("../views/Unauthorized.vue"),
  },
];
