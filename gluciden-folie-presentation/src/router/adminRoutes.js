export default [
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
    path: "/creer-categorie",
    name: "createCategory",
    component: () => import("../views/CreateCategory.vue"),
    meta: { requiresAdmin: true },
  },

  {
    path: "/modifier-catégorie/:id",
    name: "updateCategory",
    component: () => import("@/views/UpdateCategory.vue"),
    meta: { requiresAdmin: true },
  },

];
