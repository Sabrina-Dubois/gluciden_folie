<template>
	<v-container class="Recip-list-container" fluid>
		<h1>{{ $t("recipe_list.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto">
			<v-container fluid>
				<v-row dense>
					<!-- {{ recipes }} -->
					<!-- Card image & boutons -->
					<v-col
						v-for="recipe in sortedRecipes"
						:key="recipe.name"
						cols="12"
						md="3"
						class="d-flex justify-center ml-4"
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
								<v-card-title class="text-white">
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
		};
	},
	//regarder
	mounted() {
		this.fetchRecipes();
	},
	computed: {
		recipes() {
			const recipesStore = useRecipesStore();
			return recipesStore.recipes; // Utilise directement les recettes du store
		},
		sortedRecipes() {
			return this.recipes.sort((a, b) => {
				return a.name.localeCompare(b.name);
			});
		},
		imageUrl() {
			return (recipe) =>
				"/images/" + recipe.picture + "?t=" + new Date().getTime();
		},
	},

	methods: {
		async fetchRecipes() {
			const recipesStore = useRecipesStore();
			await recipesStore.fetchRecipes(); // Synchronisation avec le store
		},

		async deleteRecipe(recipeId) {
			const recipesStore = useRecipesStore();
			await recipesStore.deleteRecipe(recipeId);
			this.fetchRecipes();
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
.recip-list-container {
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
.Recip-list-container{
	background-color:  #f5ede8;
}
</style>