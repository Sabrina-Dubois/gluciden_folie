<template>
	<v-container class="category-List-container" fluid>
		<v-card class="d-flex mx-auto my-auto">
			<h1>{{ $t('category_list.title') }}</h1>
			<v-container fluid>
				<v-row dense>
					<!-- Card & boutons -->
					<v-col
						v-for="category in categories"
						:key="category.name"
						cols="12"
						md="3"
						class="d-flex justify-center ml-4"
					>
						<v-card class="category-card d-flex flex-column align-center" flat>
								<v-card-title class="text-white">
									{{ category.name }}
								</v-card-title>
							
							<v-card-actions class="button d-flex">
								<!-- <v-btn
									icon="mdi-pencil"
									@click="updateRecipe(category.id)"
								></v-btn> -->
								<v-btn 
									icon="mdi-delete" 
									@click="deleteCategory(category.id)">
								</v-btn>
							</v-card-actions>
						</v-card>
					</v-col>
				</v-row>
			</v-container>
		</v-card>
	</v-container>
</template>

<script scoped>
import apiClient from '@/api/axiosConfig';

export default {
	name: "categoriesList",
	data() {
		//fonction qui retourne un objet contenant les données réactives du composant->retourne un array items qui contient les src des images à afficher dans le carrousel.
		return {
			categories: [],
		};
	},
	//regarder
	beforeMount() {
		this.initCategories();
	},

	methods: {
		async initCategories() {
			try {
				const response = await apiClient.get("/categories");
				this.categories = response.data;
			} catch (error) {
				console.error(error);
			}
		},
		async deleteCategory(categoryId) {
			if (confirm("Veux-tu supprimer cette catégorie ?")) {
				try {
					const response = await apiClient.delete(`/categories/${categoryId}`);

					if (response.status === 204 || response.status === 200) {
						this.initCategories();
					} else {
						alert("Erreur lors de la suppression de la catégorie");
					}
				} catch (error) {
					console.error("Erreur:", error);
				}
			}
		},
	},
};
</script>

<style scoped>
.Recip-List-container {
	max-width: 900px;
}

.ml-4 {
	margin-left: 50px;
}
/* Day recipe */

.v-card-actions {
	height: 10px;
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
</style>