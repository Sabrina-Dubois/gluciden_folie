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
          this.recipes = response.data;
        }
      } catch (error) {
        console.error("Erreur lors de la récupération des recettes :", error);
      }
    },
    // Ajouter une nouvelle recette
    async addRecipe({ name, picture, difficulty, ingredientList, steps }) {
      try {
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

        /* Pour les ingrédients
        ingredients.forEach((i, index) => {
          formData.append(`ingredients[${index}].ingredient.name`, i.ingredient.name);
          formData.append(`ingredients[${index}].quantity`, i.quantity);
          formData.append(`ingredients[${index}].unityId`, i.unityId);
        });

        // Pour les étapes
        steps.forEach((s, index) => {
          formData.append(`steps[${index}].number`, s.number);
          formData.append(`steps[${index}].description`, s.description);
        }); */

        const formData = new FormData();
        if (picture) {
          formData.append("picture", picture);
        }

        formData.append("name", name);
        formData.append("difficulty", typeof difficulty === "string" ? difficulty.toUpperCase() : difficulty);

        formData.append(
          "ingredients",
          JSON.stringify(
            ingredientList.map((i) => ({
              ingredient: { name: i.ingredient.name },
              quantity: i.quantity ,
              unityId: i.unityId
            }))
          )
        );

        formData.append(
          "steps",
          JSON.stringify(
            steps.map((i) => ({
              number: i.number,
              description: i.description
            }))
          )
        );

        const response = await apiClient.post("/recipes", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });

        if (response.status === 200 || response.status === 201) {
          this.recipes.push(response.data);

          // Reset des champs du store
          this.name = "";
          this.picture = null;
          this.difficulty = 0;
          this.ingredientList = [];
          this.steps = [];

          alert("Recette créée avec succès !");
          return true;

        } else {
          console.error("Erreur lors de la création de la recette", response);
          throw new Error("La création de la recette a échoué.");
        }
      } catch (error) {
        console.log("Recette ajoutée:", response.data);
        console.error("Erreur lors de l'ajout de la recette", error);
        alert("Une erreur s'est produite lors de l'ajout de la recette.");
        return false;
      }
    },

    async updateRecipe(id, formData) {
      try {
        const response = await apiClient.put(`/recipes/${id}`, formData, {});

        // Mise à jour de la recette dans le tableau local
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

      this.recipes = this.recipes.filter((r) => r.id !== recipeId);
    },
  },
});
