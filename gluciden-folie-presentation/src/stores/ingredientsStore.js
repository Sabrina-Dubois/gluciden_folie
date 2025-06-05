import { defineStore } from "pinia";
import apiClient from "@/api/axiosConfig";

export const useIngredientsStore = defineStore("ingredients", {
  state: () => ({
    ingredientsList: [],
    unitiesList: [],
  }),

  actions: {
    // Recupérer ingrédients
    async fetchIngredients() {
      try {
        const response = await apiClient.get("/ingredients");
        if (response.status === 200) {
          this.ingredientsList = response.data;
        }
      } catch (error) {
        console.error("Erreur lors de la récupération des ingrédients");
      }
    },
    async fetchUnities() {
      try {
        const response = await apiClient.get("/unities");
        this.unitiesList = response.data;
      } catch (error) {
        console.error("Erreur lors de la récupération des unités:", error);
      }
    },
    // Ajouter un nouvel ingrédients
    async addIngredient(name, quantity, unity) {
      try {
        const data = {
          name: name,
          quantity: quantity,
          unity: unity,
        };

        const response = await apiClient.post("/ingredients", data, {
          headers: { "Content-Type": "application/json" },
        });

        console.log("Données envoyées : ", data);
        if (response.status === 200 || response.status === 201) {
          console.log("Ingrédient ajouté avec succès !");
          return true;
        } else {
          console.error("Erreur lors de l'ajout de l'ingrédient");
        }
        return false;
      } catch (error) {
        console.error("Erreur lors de l'ajout de l'ingrédient", error);
        return false;
      }
    },
  },
});
