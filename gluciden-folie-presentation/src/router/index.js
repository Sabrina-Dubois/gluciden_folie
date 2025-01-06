import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("../views/Home.vue"),
    },
    {
      path: "/test",
      name: "test",
      component: () => import("../views/Test.vue"),
    },
    {
      path: "/:action",
      name: "authentification",
      component: () => import("../views/Authentification.vue"),
    },
    {
      path: "/creer-recette",
      name: "createRecipe",
      component: () => import("../views/CreateRecipe.vue"),
    },
    {
      path: "/modifier-recette/:id",
      name: "updateRecipe",
      component: () => import("@/views/UpdateRecipe.vue"),
    },
    {
      path: "/liste-recettes",
      name: "recipesList",
      component: () => import("@/views/RecipesList.vue"),
    },
    {
      path: "/recipes/:id",
      name: "recipeDetails",
      component: () => import ("@/views/RecipeDetails.vue"),
      props: true,
    },
    {
      path: "/creer-categorie",
      name: "createCategory",
      component: () => import("../views/CreateCategory.vue"),
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
    },
  ],
});

export default router;
