<!--Ajout et gestion des catégories-->
<template>
	<div>
		<CreateCategory v-if="showCreateCategory" @categoryCreated="addCategory" />
		<CategoriesTabs :categories="categories" />
	</div>
</template>

<script>
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
	computed: {
		categories() {
			const categoriesStore = useCategoriesStore();
			return categoriesStore.categories;
		},
	},
	mounted() {
		const categoriesStore = useCategoriesStore();
		if (categoriesStore.categories.length === 0) {
			categoriesStore.fetchCategories();
		}
	},
	methods: {
		async onCategoryCreated() {
			const categoriesStore = useCategoriesStore();
			await categoriesStore.fetchCategories();
		},
	},
};
</script>