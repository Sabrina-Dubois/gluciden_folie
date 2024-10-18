//Without LAZY LOADING
import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Categories from "@/views/CreateCategorie.vue";
import Test from "../views/Test.vue";
import Connection from "../views/Connection.vue";
import Recipes from "../views/Recipes.vue";
import CreateRecipe from "../views/CreateRecipe.vue";
import RecipesList from "@/components/RecipesList.vue";
import UpdateRecipe from "@/views/UpdateRecipe.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: Home,
    },
    {
      path: "/categories",
      name: "categories",
      component: Categories,
    },
    {
      path: "/test",
      name: "test",
      component: Test,
    },
    {
      path: "/connexion", 
      name: "connection",
      component: Connection,
    },
    {
      path: "/recettes", // a faire en francais
      name: "recipes",
      component: Recipes,
    },
    {
      path: "/creer-recette",
      name: "createRecipe",
      component: CreateRecipe,
    },
    {
      path: "/modifier-recette/:id", // a faire en francais
      name: "updateRecipe",
      component: UpdateRecipe,
    },
    {
      path: "/liste-recettes", 
      name: "recipesList",
      component: RecipesList,
    },
  ],
});
export default router;


// LAZY LOADING -> Import()
// import { createRouter, createWebHistory } from "vue-router";

// const router = createRouter({
//   history: createWebHistory(import.meta.env.BASE_URL),
//   routes: [
//     {
//       path: "/",
//       name: "home",
//       component: () => import("../views/Home.vue"),
//     },
//     {
//       path: "/categories",
//       name: "categories",
//       component: () => import("@/views/Categories.vue"),
//     },
//     {
//       path: "/test",
//       name: "test",
//       component: () => import("../views/Test.vue"),
//     },
//     {
//       path: "/connexion",
//       name: "connection",
//       component: () => import("../views/Connection.vue"),
//     },
//     {
//       path: "/recettes", // a faire en francais
//       name: "recipes",
//       component: () => import("../views/Recipes.vue"),
//     },
//     {
//       path: "/creer-recette",
//       name: "createRecipe",
//       component: () => import("../views/CreateRecipe.vue"),
//     },
//     {
//       path: "/modifier-recette/:id", // a faire en francais
//       name: "updateRecipe",
//       component: () => import("@/views/UpdateRecipe.vue"),
//     },
//     {
//       path: "/liste-recettes",
//       name: "recipesList",
//       component: () => import("@/components/RecipesList.vue"),
//     },
//   ],
// });

// export default router;
