<template>
	<div >
		<!-- création de la catégorie et envoi de l'évènement -->
		<CreateCategory v-if="showCreateCategory" @categoryCreated="addCategory" />
		<CategoriesTabs :categories="categories" />
	</div>
</template>

<script>
import apiClient from "../api/axiosConfig"
import CreateCategory from "../views/CreateCategory.vue";
import CategoriesTabs from "./CategoriesTabs.vue";

export default {

	name: "CategoriesLayout",
	components: {
		CreateCategory,
		CategoriesTabs,
	},
    props: {
        showCreateCategory: {
            type: Boolean,
            default: true
        }
    },
	data() {
		return {
			categories: [],
		};
	},
	mounted() {
		this.fetchCategories();
	},
	methods: {
		async fetchCategories() {
			try {
				const response = await apiClient.get("/categories");
				this.categories = response.data;
			} catch (error) {
				console.error("Erreur lors de la récupératio des catégories:", error);
			}
		},
		async addCategory(newCategory) {
			try {
				await apiClient.post("/categories", { name: newCategory });
				this.categories.push({ name: newCategory });
			} catch (error) {
				console.error("Erreur lors de la récupératio des catégories:", error);
			}
		},
	},
};
</script>

<style>
</style>