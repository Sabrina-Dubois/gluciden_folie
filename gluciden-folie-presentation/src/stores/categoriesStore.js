import { defineStore } from "pinia";
import apiClient from "@/api/axiosConfig";

export const useCategoriesStore = defineStore("categories", {
  state: () => ({
    categories: [],
  }),
  actions: {
    // Récup catégorie depuis API
    async fetchCategories() {
        try {
          const response = await apiClient.get("/categories");
          this.categories = response.data;
        } catch (error) {
          console.error("Erreur lors de la récupératio des catégories:", error);
        }
      
    },

    //Ajouter une nouvelle catégorie
    async addCategory(categoryName) {
      try {
        const response = await apiClient.post("/categories", { name: categoryName });
        // Ajout catégorie complète(ID)
        this.categories = [...this.categories, response.data];
        console.log("Catégorie ajoutée avec succès:", response.data);
      } catch (error) {
        console.error("Erreur lors de la récupératio des catégories:", error);
      }
    },
    // Mettre a jour la catégorie
    async updateCategory(updatedCategory) {
      try {
        const response = await apiClient.put(`/categories/${updatedCategory.id}`, updatedCategory);
        const index = this.categories.findIndex((cat) => cat.id === updatedCategory.id);
        if (index !== -1) {
          this.categories[index] = response.data; // Met à jour la catégorie dans la liste
        }
        console.log("Catégorie mise à jour avec succès:", response.data);
      } catch (error) {
        console.error("Erreur lors de la mise à jour de la catégorie:", error);
      }
    },
    // Supprimer une catégorie
    async deleteCategory(categoryId) {
      try {
        await apiClient.delete(`/categories/${categoryId}`);
        this.categories = this.categories.filter((cat) => cat.id !== categoryId); // Retirer la catégorie de la liste
        console.log("Catégorie supprimée avec succès");
      } catch (error) {
        console.error("Erreur lors de la suppression de la catégorie:", error);
      }
    },
  },
});
