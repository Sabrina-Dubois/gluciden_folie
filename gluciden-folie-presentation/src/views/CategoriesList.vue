<template>
	<v-container class="category-list-container" fluid>
		<h1 class="page-title">{{ $t("category_list.title") }}</h1>

		<v-card class="mx-auto my-6 px-4 py-4" elevation="4">
			<v-container fluid>
				<v-row dense align="stretch">
					<v-col
						v-for="category in categories"
						:key="category.id"
						cols="12"
						sm="6"
						md="3"
						class="d-flex"
					>
						<!-- ✅ Chaque carte occupe toute la hauteur -->
						<v-card class="category-card d-flex flex-column" flat>
							<v-card-title class="title-category text-center">
								{{ category.name }}
							</v-card-title>

							<v-spacer></v-spacer>

							<v-card-actions class="button d-flex justify-center">
								<v-btn
									icon="mdi-pencil"
									@click="updateCategory(category.id)"
								></v-btn>
								<v-btn icon="mdi-delete" @click="deleteCategory(category.id)">
								</v-btn>
							</v-card-actions>
						</v-card>
					</v-col>
				</v-row>
			</v-container>
		</v-card>
	</v-container>
</template>

<script>
import { useCategoriesStore } from "@/stores/categoriesStore.js";

export default {
	name: "categoriesList",
	computed: {
		categories() {
			return useCategoriesStore().categories;
		},
	},
	methods: {
		deleteCategory(categoryId) {
			useCategoriesStore().deleteCategory(categoryId);
		},
		updateCategory(categoryId) {
			this.$router.push({ name: "updateCategory", params: { id: categoryId } });
		},
	},
	created() {
		useCategoriesStore().fetchCategories();
	},
};
</script>

<style scoped>
/* *** Container *** */
.category-list-container {
	max-width: 900px;
	margin: auto;
}

/* *** Titre *** */
.title-category {
	color: #5d827f;
	font-size: 18px;
	text-align: center;
}

/* *** Cards *** */
.category-card {
	background-color: #d3beb1;
	border-radius: 12px;
	padding: 16px;
	width: 100%;
	min-height: 150px; 
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

/* *** Boutons *** */
.button {
	justify-content: center;
}
.v-btn {
	color: #5d827f;
	background-color: #f5ede8;
	margin: 0 5px;
	transition: all 0.3s ease;
}
.v-btn:hover,
.v-btn--active {
	background-color: #5d827f;
	color: #f5ede8 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}
</style>
