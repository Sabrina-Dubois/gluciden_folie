// src/stores/recipesStore.js
import { defineStore } from "pinia";
import apiClient from "../api/axiosConfig"; // Axios pré-configuré pour l'API

export const useRecipesStore = defineStore("recipes", {
  state: () => ({
    recipes: [], // Liste de toutes les recettes
    name: "", // Nom d'une nouvelle recette
    picture: null,
    difficulty: 0, // Difficulté d'une nouvelle recette
    ingredientList: [], // Liste des ingrédients pour la recette
    steps: [], // Liste des étapes pour la recette
  }),

  actions: {
    /**
     * Récupérer toutes les recettes depuis l'API
     */
    async fetchRecipes() {
      try {
        console.log("Avant la requête");

        const response = await apiClient.get("/recipes");

        if (Array.isArray(response.data)) {
          this.recipes = response.data;
        } else if (response.data) {
          this.recipes = [response.data];
        } else {
          this.recipes = [];
        }

        console.log("Recettes chargées:", this.recipes);
        return this.recipes;
      } catch (error) {
        console.error("Erreur lors de la récupération des recettes :", error);
        this.recipes = [];
      }
    },

    /**
     * Ajouter une nouvelle recette
     * @param {Object} param0
     */
    async addRecipe({ name, picture, difficulty, ingredientList, steps }) {
  try {
    const formData = new FormData();
    
    // 1. Champs de base
    formData.append("name", name);
    formData.append("difficulty", difficulty);
    if (picture) formData.append("picture", picture);

    // 2. Formatage EXACT des ingrédients
    ingredientList.forEach((ingredient, index) => {
      // Structure EXACTE comme dans votre exemple
      formData.append(`ingredients[${index}].ingredient.name`, ingredient.ingredient.name);
      formData.append(`ingredients[${index}].quantity`, ingredient.quantity.toString());
      formData.append(`ingredients[${index}].unityId`, ingredient.unityId.toString());
    });

    // 3. Formatage des étapes
    steps.forEach((step, index) => {
      formData.append(`steps[${index}].number`, (index + 1).toString());
      formData.append(`steps[${index}].description`, step.description);
    });

    // 4. Envoi avec headers
    const response = await apiClient.post("/recipes", formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    });

    return response.data;
  } catch (error) {
    console.error("Erreur complète:", {
      request: [...formData.entries()],
      response: error.response?.data
    });
    throw error;
  }
},
    /**
     * Mettre à jour une recette existante
     * @param {Number} id - ID de la recette
     * @param {FormData} formData - Données à mettre à jour
     */
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

    /**
     * Supprimer une recette
     * @param {Number} recipeId - ID de la recette à supprimer
     */
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
