<template>
	<v-container class="Recip-list-container" fluid>
		<h1>{{ $t("recipe_list.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto" elevation="4">
			<v-container fluid>
				<v-row dense>
					<!-- Card image & boutons -->
					<v-col
						v-for="recipe in sortedRecipes"
						:key="recipe.id"
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
								class="recipe-picture"
								height="200px"
								cover
								rounded
							>
								<v-card-title class="title-recipe">
									{{ recipe.name }}
								</v-card-title>
							</v-img>
							<v-card-actions class="button d-flex">
								<v-btn icon="mdi-heart"></v-btn>
								<v-btn
									icon="mdi-pencil"
									@click.stop="updateRecipe(recipe.id)"
								></v-btn>
								<v-btn
									icon="mdi-delete"
									@click.stop="deleteRecipe(recipe.id)"
								></v-btn>
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

export default {
	name: "recipesList",
	//data() {
	//return {
	//recipesStore: useRecipesStore(), // instance unique du store
	//..};
	//},
	mounted() {
		const recipesStore = useRecipesStore();
		recipesStore
			.fetchRecipes()
			.then(() => console.log("Recettes chargées:", recipesStore.recipes))
			.catch((err) => console.error(err));
	},
	computed: {
		recipesStore() {
			// 🔧 On ne le met plus dans data(), on l'appelle directement ici pour qu'il reste réactif
			return useRecipesStore();
		},
		// On récupère les recettes depuis le store
		recipes() {
			// On protège contre les données non valides
			return Array.isArray(this.recipesStore.recipes)
				? this.recipesStore.recipes
				: [];
		},
		sortedRecipes() {
			return this.recipes
				.filter((recipe) => recipe.name) // filtrer les recettes sans nom
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
				await this.recipesStore.fetchRecipes();
				console.log("Recettes après fetch :", this.recipesStore.recipes);
				// 🔧 Ajouté pour vérifier que les données sont bien récupérées
			} catch (error) {
				console.error("Erreur lors de la récupération des recettes :", error);
			}
		},
		async deleteRecipe(recipeId) {
			try {
				await this.recipesStore.deleteRecipe(recipeId);
				// rafraîchit la liste après suppression
				await this.recipesStore.fetchRecipes();
			} catch (error) {
				console.error("Erreur lors de la suppression :", error);
			}
		},
		updateRecipe(recipeId) {
			this.$router.push({ name: "updateRecipe", params: { id: recipeId } });
		},
		goToRecipeDetails(recipeId) {
			this.$router.push({ name: "recipeDetails", params: { id: recipeId } });
		},
	},
};
</script>

<style scoped>
.Recip-list-container {
	background-color: #f5ede8;
	max-width: 900px;
	margin: auto;
}

.title-recipe {
	color: #5d827f;
	font-size: 18px;
	text-align: center;
}

.button {
	justify-content: center;
}

.v-btn {
	color: #5d827f;
	margin: 0;
	background-color: #f5ede8;
}

.v-btn:hover,
.v-btn--active {
	background-color: #5d827f;
	color: #f5ede8 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.recipe-picture {
	width: 200px;
	height: 200px;
	object-fit: cover;
	border-radius: 8px;
}
</style>
