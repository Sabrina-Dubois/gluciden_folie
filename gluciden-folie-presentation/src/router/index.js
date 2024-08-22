import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CategoriesView from "../views/CategoriesView.vue";
import TestView from "../views/TestView.vue";
import ConnectionView from "../views/ConnectionView.vue";
import RecipesView from "../views/RecipesView.vue";
import CreateRecipeView from "../views/CreateRecipeView.vue";
import RecipesList from "@/components/RecipesList.vue";
import UpdateRecipeView from "@/views/UpdateRecipeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/categories",
      name: "categories",
      component: CategoriesView,
    },
    {
      path: "/test",
      name: "test",
      component: TestView,
    },
    {
      path: "/connexion", 
      name: "connection",
      component: ConnectionView,
    },
    {
      path: "/recettes", // a faire en francais
      name: "recipes",
      component: RecipesView,
    },
    {
      path: "/creer-recette",
      name: "createRecipe",
      component: CreateRecipeView,
    },
    {
      path: "/modifier-recette/:id", // a faire en francais
      name: "updateRecipe",
      component: UpdateRecipeView,
    },
    {
      path: "/liste-recettes", 
      name: "recipesList",
      component: RecipesList,
    },
  ],
});

export default router;
