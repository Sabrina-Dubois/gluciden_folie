<template>
	<v-container class="Recip-list-container" fluid>
		<h1>{{ $t("recipe_list.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto" elevation="4">
			<v-container fluid>
				<v-row dense>
					<!-- Card image & boutons -->
					<v-col
						v-for="recipe in sortedRecipes"
						:key="recipe.name"
						cols="12"
						sm="6"
						md="3"
						class="d-flex justify-center"
					>
						<v-card
							class="recip-card d-flex flex-column align-center"
							@click="goToRecipeDetails(recipe.id)"
							flat
						>
							<v-img
								:key="recipe.picture + recipe.id"
								:src="imageUrl(recipe)"
								click.stop="goToRecipeDetails(recipe.id)"
								class="recipe-picture"
								height="200px"
								cover
								rounded=""
							>
								<v-card-title class="title-recipe">
									{{ recipe.name }}
								</v-card-title>
							</v-img>
							<v-card-actions class="button d-flex">
								<v-btn icon="mdi-heart"></v-btn>
								<!--<v-btn icon="mdi-share-variant"></v-btn>-->
								<v-btn
									icon="mdi-pencil"
									@click.stop="updateRecipe(recipe.id)"
								></v-btn>
								<v-btn icon="mdi-delete" @click.stop="deleteRecipe(recipe.id)">
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
import { useRecipesStore } from "@/stores/recipesStore";
import RecipeDetails from "./RecipeDetails.vue";

export default {
	name: "recipesList",
	components: RecipeDetails,
	data() {
		return {
			recipesStore: useRecipesStore(), // on instancie une fois dans data
		};
	},
	//regarder
	mounted() {
		this.fetchRecipes();
	},
	computed: {
		recipes() {
			return this.recipesStore.recipes;
		},
		sortedRecipes() {
			if (!Array.isArray(this.recipes)) {
				console.error("recipes n'est pas un tableau", this.recipes);
				return []; // Retourne un tableau vide si ce n'est pas un tableau
			}
			return this.recipes
				.filter((recipe) => recipe.name) // Filtrer les recettes sans nom
				.sort((a, b) => a.name.localeCompare(b.name));
		},
		imageUrl() {
			return (recipe) =>
				"/images/" + recipe.picture + "?t=" + new Date().getTime();
		},
	},

	methods: {
		async fetchRecipes() {
			try {
				await this.recipesStore.fetchRecipes(); // appel unique
			} catch (error) {
				console.error("Erreur lors de la récupération des recettes :", error);
			}
		},

		async deleteRecipe(recipeId) {
			try {
				await this.recipesStore.deleteRecipe(recipeId);

				// Après suppression, on rafraîchit la liste des recettes dans le store
				await this.recipesStore.fetchRecipes();
			} catch (error) {
				console.error("Erreur lors de la suppression :", error);
			}
		},
		//renvoyer vers la page update
		updateRecipe(recipeId) {
			this.$router.push({ name: "updateRecipe", params: { id: recipeId } });
		},
		// Rediriger vers la page des détails de la recette lorsque l'image est cliquée
		goToRecipeDetails(recipeId) {
			this.$router.push({ name: "recipeDetails", params: { id: recipeId } });
		},
	},
};
</script>

<style scoped>
/* *** Container *** */
.recip-list-container {
	max-width: 900px;
}
.ml-4 {
	margin-left: 50px;
}
.Recip-list-container {
	background-color: #f5ede8;
}

/* *** Titre *** */
.title-recipe {
	color: #5d827f;
	font-size: 18px;
	text-align: center
}
.v-app-bar-title {
	flex: 1;
	text-align: start;
	font-size: 40px;
	font-weight: bold !important;
	color: #5d827f;
}

/* *** Boutons *** */
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

/* *** Photo *** */
.recipe-picture {
	width: 200px;
	height: 200px;
	object-fit: cover; /* image couvre toute la surface sans déformation */
	border-radius: 8px;
}
</style>