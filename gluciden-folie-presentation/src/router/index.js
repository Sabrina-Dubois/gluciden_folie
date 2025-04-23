// Import nécessaire
import { createRouter, createWebHistory } from "vue-router";

// Déclaration du routeur avec les routes
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
  ],
});

// 🔒 Guard global de sécurité
router.beforeEach((to, from, next) => {
  // Récupère le token JWT depuis le localStorage
  const token = localStorage.getItem("jwt");

  // Si la route demande un rôle admin
  if (to.meta.requiresAdmin) {
    // Pas connecté → redirection vers login
    if (!token) {
      return next({ name: "login" });
    }

    try {
      // Décodage du token pour extraire le rôle
      const payload = JSON.parse(atob(token.split(".")[1]));

      // Si le rôle est ROLE_ADMIN → OK
      if (payload.role === "ROLE_ADMIN") {
        return next();
      } else {
        // Le user est connecté mais pas admin → redirection refus
        return next({ name: "unauthorized" });
      }
    } catch (error) {
      console.error("Token invalide :", error);
      return next({ name: "login" }); // Token cassé → on renvoie au login
    }
  }

  // Aucune restriction → accès autorisé
  next();
});

export default router;
