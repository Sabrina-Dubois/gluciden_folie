import { defineStore } from "pinia";
import apiClient from "../api/axiosConfig";

export const useRecipesStore = defineStore("recipes", {
  state: () => ({
    recipes: [],
    name: "",
    picture: null,
    difficulty: 0,
    ingredientList: [],
    steps: [],
  }),

  actions: {
    // Récupérer les recettes
    async fetchRecipes() {
      try {
        const response = await apiClient.get("/recipes");
        if (response.status === 200) {
          this.recipes = Array.isArray(response.data) ? response.data : [response.data];
          return this.recipes;
        }
      } catch (error) {
        console.error("Erreur lors de la récupération des recettes :", error);
      }
    },
    // Ajouter une nouvelle recette
    async addRecipe({ name, picture, difficulty, ingredientList, steps }) {
      try {
        if (!ingredientList || ingredientList.length === 0) {
          throw new Error("Vous devez ajouter au moins un ingrédient.");
        }

        // Création du FormData
        const formData = new FormData();
        formData.append("name", name);
        formData.append("difficulty", difficulty.toString().toUpperCase());

        if (picture) {
          formData.append("picture", picture);
        }

        // Ajout des ingrédients (notation avec crochets pour que Spring les parse correctement)
        ingredientList.forEach((i, index) => {
          if (i.ingredient.id) {
            formData.append(`ingredients[${index}].ingredient.id`, i.ingredient.id);
          } else {
            formData.append(`ingredients[${index}].ingredient.name`, i.ingredient.name);
          }
          formData.append(`ingredients[${index}].quantity`, i.quantity);
          formData.append(`ingredients[${index}].unityId`, i.unityId);
        });

        // Ajout des étapes
        steps.forEach((s, index) => {
          formData.append(`steps[${index}].number`, s.number);
          formData.append(`steps[${index}].description`, s.description);
        });

        // Appel API
        const response = await apiClient.post("/recipes", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });

        // Mise à jour du store
        this.recipes.push(response.data);
        this.name = "";
        this.picture = null;
        this.difficulty = 0;
        this.ingredientList = [];
        this.steps = [];

        alert("Recette créée avec succès !");
        return true;
      } catch (error) {
        console.error("Erreur lors de l'ajout de la recette :", error);
        alert(error.message || "Une erreur s'est produite lors de l'ajout de la recette.");
        return false;
      }
    },

    async updateRecipe(id, formData) {
      try {
        const response = await apiClient.put(`/recipes/${id}`, formData);
        const index = this.recipes.findIndex((r) => r.id === id);
        if (index !== -1) {
          this.recipes[index] = response.data;
        }
        return true;
      } catch (error) {
        console.error("Erreur lors de la mise à jour de la recette:", error);
        return false;
      }
    },

    async deleteRecipe(recipeId) {
      try {
        await apiClient.delete(`/recipes/${recipeId}`);
        this.recipes = this.recipes.filter((r) => r.id !== recipeId);
      } catch (error) {
        console.error("Erreur lors de la suppression de la recette :", error);
      }
    },
  },
});
