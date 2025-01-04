<template>
	<v-container class="Recip-list-container" fluid>
		<h1>{{ $t("recipe_list.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto">
			<v-container fluid>
				<v-row dense>
					<!-- {{ recipes }} -->
					<!-- Card image & boutons -->
					<v-col
						v-for="recipe in recipes"
						:key="recipe.name"
						cols="12"
						md="3"
						class="d-flex justify-center ml-4"
					>
						<v-card class="recip-card d-flex flex-column align-center" flat>
							<v-img
								:key="recipe.picture + recipe.id"
								:src="
									'/images/' + recipe.picture + '?t=' + new Date().getTime()
								"
								class="recipe-picture"
								height="200px"
								cover
								rounded=""
							>
								<v-card-title class="text-white">
									{{ recipe.name }}
								</v-card-title>
							</v-img>
							<v-card-actions class="button d-flex">
								<v-btn icon="mdi-heart"></v-btn>
								<v-btn icon="mdi-share-variant"></v-btn>
								<v-btn
									icon="mdi-pencil"
									@click="updateRecipe(recipe.id)"
								></v-btn>
								<v-btn icon="mdi-delete" @click="deleteRecipe(recipe.id)">
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
import apiClient from "@/api/axiosConfig";

export default {
	name: "recipesList",
	data() {
		//fonction qui retourne un objet contenant les données réactives du composant->retourne un array items qui contient les src des images à afficher dans le carrousel.
		return {
			recipes: [],
		};
	},
	//regarder
	beforeMount() {
		this.initRecipes();
	},
	beforeRouteEnter(to, from, next) {
		// Appel de la méthode `initRecipes` après la création du composant
		next((vm) => {
			vm.initRecipes(); // Ici on appelle la méthode pour récupérer les recettes
		});
	},

	methods: {
		async initRecipes() {
			try {
				const response = await apiClient.get("/recipes");
				this.recipes = response.data;
			} catch (error) {
				console.error(error);
			}
		},
		async deleteRecipe(recipeId) {
			if (confirm("Veux-tu supprimer cette recette ?")) {
				try {
					const response = await apiClient.delete(`/recipes/${recipeId}`);
					if (response.status === 204 || response.status === 200) {
						this.initRecipes();
						alert("Recette supprimée");
					} else {
						alert("Erreur lors de la suppression de la recette");
					}
				} catch (error) {
					console.error("Erreur:", error);
				}
			}
		},
		//renvoyer vers la page update
		updateRecipe(recipeId) {
			this.$router.push({ name: "updateRecipe", params: { id: recipeId } });
		},
	},
};
</script>

<style scoped>
.recip-list-container {
	max-width: 900px;
}

.ml-4 {
	margin-left: 50px;
}

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