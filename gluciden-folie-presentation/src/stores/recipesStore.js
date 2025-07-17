import { defineStore } from "pinia";
import apiClient from "../api/axiosConfig";

export const useRecipesStore = defineStore("recipes", {
  state: () => ({
    recipes: [],
    name: "",
    picture: null,
    difficulty: 0,
    ingredientList: [],
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
    async addRecipe({ name, picture, difficulty, ingredientList,steps }) {
      try {
        // Validation des ingrédients
        ingredientList.forEach((ingredient) => {
          if (
            !ingredient.ingredient ||
            !ingredient.ingredient.name ||
            !ingredient.quantity ||
            ingredient.quantity <= 0
          ) {
            throw new Error("Chaque ingrédient doit avoir un nom et une quantité.");
          }
        });

        const formData = new FormData();
        if (picture) {
          formData.append("picture", picture);
        }
        formData.append("name", name);
        formData.append("difficulty", difficulty);
        formData.append("ingredients", JSON.stringify(ingredientList));
        formData.append("steps", JSON.stringify(steps));

        const response = await apiClient.post("/recipes", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });

        // Vérification de la réponse
        if (response.status === 200 || response.status === 201) {
          const newRecipe = response.data;
          console.log("Recette reçue du backend avant la validation :", newRecipe); // Log détaillé avant validation

          // Validation de la réponse
          if (!newRecipe || typeof newRecipe !== "object" || !newRecipe.id || !newRecipe.name) {
            console.error("La recette renvoyée a un format inattendu :", newRecipe);
            throw new Error("La recette renvoyée est invalide.");
          }

          // Ajoute la recette à la liste
          newRecipe.ingredients = ingredientList;
          this.recipes.push(newRecipe);

          // Réinitialisation des champs
          this.name = "";
          this.picture = null;
          this.difficulty = 0;
          this.ingredientList = [];

          alert("Recette créée avec succès !");
          console.log("Recette créée avec succès !");
          return true;
        } else {
          console.error("Erreur lors de la création de la recette", response);
          throw new Error("La création de la recette a échoué.");
        }
      } catch (error) {
        console.error("Erreur lors de l'ajout de la recette", error);
        alert("Une erreur s'est produite lors de l'ajout de la recette.");
        return false;
      }
    },
    async updateRecipe(id, formData) {
      try {
        console.log("Payload envoyé pour mise à jour :", formData);
        const response = await apiClient.put(`/recipes/${id}`, formData, {
          
        });

        // Mise à jour de la recette dans le tableau local si besoin
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
      await apiClient.delete(`/recipes/${recipeId}`);
      // Option 1 : refaire un fetch complet
      //await this.fetchRecipes();

      // Option 2 : (plus optimisé) supprimer localement sans refaire un fetch
      this.recipes = this.recipes.filter((r) => r.id !== recipeId);
    },
  },
});

