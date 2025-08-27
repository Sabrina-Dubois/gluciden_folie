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
    async fetchRecipes() {
      try {
        const response = await apiClient.get("/recipes");

        if (Array.isArray(response.data)) {
          this.recipes = response.data;
        } else if (response.data) {
          this.recipes = [response.data];
        } else {
          this.recipes = [];
        }
        return this.recipes;
      } catch (error) {
        console.error("Erreur lors de la récupération des recettes :", error);
        this.recipes = [];
      }
    },

    async addRecipe({ name, picture, difficulty, ingredientList, steps }) {
      const formData = new FormData();
      try {
        //const formData = new FormData();
        formData.append("name", name);
        formData.append("difficulty", difficulty);
        if (picture) formData.append("picture", picture);

        //ingredientList.forEach((ingredient, index) => {
        //formData.append(`ingredients[${index}].ingredient.name`, ingredient.ingredient.name);
        //formData.append(`ingredients[${index}].quantity`, ingredient.quantity.toString());
        //
        //formData.append(`ingredients[${index}].unityId`, ingredient.unityId.toString());
        //});

        //steps.forEach((step, index) => {
        //formData.append(`steps[${index}].number`, (index + 1).toString());
        //formData.append(`steps[${index}].description`, step.description);
        //});

        formData.append("ingredientsJson", JSON.stringify(ingredientList));
        formData.append("stepsJson", JSON.stringify(steps));

        const response = await apiClient.post("/recipes", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        return response.data;
      } catch (error) {
        console.error("Erreur complète:", {
          request: formData ? [...formData.entries()] : null,
          response: error.response?.data,
        });
        throw error;
      }
    },
    async updateRecipe(id, formData) {
      try {
        const response = await apiClient.put(`/recipes/${id}`, formData);
        const index = this.recipes.findIndex((r) => r.id === id);
        if (index !== -1) this.recipes[index] = response.data;
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
