import { defineStore } from "pinia";
import apiClient from "../api/axiosConfig";

export const useRecipesStore = defineStore("recipes", {
  state: () => ({
    recipes: [],
  }),
  actions: {
    // Récupérer les recettes
    async fetchRecipes() {
      try {
        const response = await apiClient.get("/recipes");
        if (response.status === 200) {
          this.recipes = response.data;
        }
      } catch (error) {
        console.error("Erreur lors de la récupération des recettes :", error);
      }
    },

    // Ajouter une nouvelle recette
    async addRecipe(recipeName, recipePicture) {
      try {
        const formData = new FormData();
        formData.append("name", recipeName);
        formData.append("picture", recipePicture);
       
        console.log("Envoi de la requête avec les données:", formData);

        const response = await apiClient.post("/recipes", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });
        console.log("Réponse de l'API:", response);

        if (response.status === 200 || response.status === 201) {
          console.log("Recette créée avec succès !");
          this.recipes.push(response.data);
          //await this.fetchRecipes();
          // Forcer Vue à rafraîchir la vue après l'ajout
          
            //this.$router.push({ name: "recipe-list" });

          return true;
        } else {
          console.error("Erreur lors de la création de la recette");
        }
        return false;
      } catch (error) {
        console.error("Erreur lors de l'ajout de la recette", error);
        return false;
      }
    },

    // Mettre à jour une recette
    async updateRecipe(recipeId, recipeName, formData) {
      try {
        const response = await apiClient.put(`/recipes/${recipeId}`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        if (response.status === 200) {
          const updatedRecipe = response.data;
          // Mettre à jour la recette dans le tableau local
          const index = this.recipes.findIndex((recipe) => recipe.id === recipeId);
          if (index !== -1) {
            this.recipes[index] = updatedRecipe;
          }
          return true;
        }
        return false;
      } catch (error) {
        console.error("Erreur lors de la mise à jour de la recette", error);
        return false;
      }
    },

    // Supprimer une recette
    async deleteRecipe(recipeId) {
      if (confirm("Veux-tu supprimer cette recette ?")) {
        try {
          const response = await apiClient.delete(`/recipes/${recipeId}`);
          if (response.status === 204 || response.status === 200) {
            // Supprimer la recette du tableau localement
            this.recipes = this.recipes.filter((recipe) => recipe.id !== recipeId);
            alert("Recette supprimée");
          } else {
            alert("Erreur lors de la suppression de la recette");
          }
        } catch (error) {
          console.error("Erreur:", error);
        }
      }
    },
  },
});
