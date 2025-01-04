//Ajout et gsetion des catégories
<template>
	<div>
		<!-- création de la catégorie et envoi de l'évènement -->
		<CreateCategory v-if="showCreateCategory" @categoryCreated="addCategory" />
		<CategoriesTabs :categories="categories" />
	</div>
</template>

<script>
import apiClient from "../api/axiosConfig";
import CreateCategory from "../views/CreateCategory.vue";
import CategoriesTabs from "./CategoriesTabs.vue";
import { useCategoriesStore } from "@/stores/categoriesStore.js";

export default {
	name: "CategoriesLayout",
	components: {
		CreateCategory,
		CategoriesTabs,
	},
	props: {
		showCreateCategory: {
			type: Boolean,
			default: true,
		},
	},
	data() {
		return {
			categories: [],
		};
	},
	mounted() {
		const categoriesStore = useCategoriesStore();
		categoriesStore.fetchCategories();
		this.categories = categoriesStore.categories;
	},
	methods: {
		addCategory(categoryName) {
			useCategoriesStore().addCategory(categoryName);
		},
		// methods: {
		// 	async fetchCategories() {
		// 		try {
		// 			const response = await apiClient.get("/categories");
		// 			this.categories = response.data;
		// 		} catch (error) {
		// 			console.error("Erreur lors de la récupératio des catégories:", error);
		// 		}
		// 	},
		// 	async addCategory(newCategory) {
		// 		try {
		// 			//Créer la cat -> serveur
		// 			const response = await apiClient.post("/categories", {
		// 				newCategory,
		// 			});
		// 			// Ajout catégorie complète(ID)
		// 			this.categories.push(response.data);

		// 			console.log("Catégorie ajoutée avec succès:", response.data);
		// 		} catch (error) {
		// 			console.error("Erreur lors de la récupératio des catégories:", error);
		// 		}
		// 	},
		// },
	},
};
</script>

<style>
</style>