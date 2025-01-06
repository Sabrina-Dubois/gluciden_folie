<template>
	<v-container class="category-list-container" fluid>
		<h1>{{ $t("category_list.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto">
			<v-container fluid>
				<v-row dense>
					<v-col
						v-for="category in categories"
						:key="category.name"
						cols="12"
						md="3"
						class="d-flex justify-center ml-4"
					>
						<v-card class="category-card d-flex flex-column align-center" flat>
							<v-card class="cards-list"
								><v-card-title class="text-white">
									{{ category.name }}
								</v-card-title>

								<v-card-actions class="button d-flex">
									<v-btn
										icon="mdi-pencil"
										@click="updateCategory(category.id)"
									></v-btn>
									<v-btn icon="mdi-delete" @click="deleteCategory(category.id)">
									</v-btn>
								</v-card-actions>
							</v-card>
						</v-card>
					</v-col>
				</v-row>
			</v-container>
		</v-card>
	</v-container>
</template>

<script>
import { useCategoriesStore } from "@/stores/categoriesStore.js";
//import apiClient from "@/api/axiosConfig";

export default {
	name: "categoriesList",
	computed: {
		categories() {
			return useCategoriesStore().categories;
		},
	},
	methods: {
		deleteCategory(categoryId) {
			useCategoriesStore().deleteCategory(categoryId); //store
		},
		//renvoyer vers la page update
		updateCategory(categoryId) {
			this.$router.push({ name: "updateCategory", params: { id: categoryId } });
		},
	},
	created() {
		useCategoriesStore().fetchCategories();// recup cat du store à la création du comp
	},
};
</script>

<style scoped>
.category-list-container {
	max-width: 900px;
}

.ml-4 {
	margin-left: 50px;
}

.button {
	justify-content: center;
}
.v-btn {
	color: #5d827f;
	margin: 0px 00px;
	background-color: #f5ede8;
}

.v-btn:hover,
.v-btn--active {
	background-color: #5d827f;
	color: #f5ede8 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.fav-btn {
	color: #f29eb0;
}

.fav-btn:hover,
.fav-btn--active {
	background-color: #5d827f;
	color: #f29eb0 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.v-app-bar-title {
	flex: 1;
	text-align: start;
	font-size: 40px;
	font-weight: bold !important;
	color: #5d827f;
}
.cards-list {
	background-color: #d3beb1;
}
</style>