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
      path: "/connection", // a faire en francais
      name: "connection",
      component: ConnectionView,
    },
    {
      path: "/recipes", // a faire en francais
      name: "recipes",
      component: RecipesView,
    },
    {
      path: "/createRecipe", // a faire en francais
      name: "createRecipe",
      component: CreateRecipeView,
    },
    {
      path: "/updateRecipe/:id", // a faire en francais
      name: "updateRecipe",
      component: UpdateRecipeView,
    },
    {
      path: "/recipesList", // a faire en francais
      name: "recipesList",
      component: RecipesList,
    },
  ],
});

export default router;
