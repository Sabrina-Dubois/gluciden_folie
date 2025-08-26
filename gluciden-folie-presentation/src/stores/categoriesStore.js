import { defineStore } from "pinia";
import apiClient from "@/api/axiosConfig";

export const useCategoriesStore = defineStore("categories", {
  state: () => ({
    categories: [],
  }),
  actions: {
    async fetchCategories() {
        try {
          const response = await apiClient.get("/categories");
          this.categories = response.data;
        } catch (error) {
          console.error("Erreur lors de la récupératio des catégories:", error);
        }
      
    },

    async addCategory(categoryName) {
      try {
        const response = await apiClient.post("/categories", { name: categoryName });
        this.categories = [...this.categories, response.data];
      } catch (error) {
        console.error("Erreur lors de la récupératio des catégories:", error);
      }
    },
    async updateCategory(updatedCategory) {
      try {
        const response = await apiClient.put(`/categories/${updatedCategory.id}`, updatedCategory);
        const index = this.categories.findIndex((cat) => cat.id === updatedCategory.id);
        if (index !== -1) {
          this.categories[index] = response.data;
        }
      } catch (error) {
        console.error("Erreur lors de la mise à jour de la catégorie:", error);
      }
    },
    async deleteCategory(categoryId) {
      try {
        await apiClient.delete(`/categories/${categoryId}`);
        this.categories = this.categories.filter((cat) => cat.id !== categoryId);
      } catch (error) {
        console.error("Erreur lors de la suppression de la catégorie:", error);
      }
    },
  },
});
