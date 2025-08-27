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
								:alt="recipe.name"
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
								<v-btn
									icon="mdi-heart"
									aria-label="$t('recipe_list.addToFavorites')"
								></v-btn>
								<v-btn
									icon="mdi-pencil"
									@click.stop="updateRecipe(recipe.id)"
									aria-label="$t('recipe_list.update')"
								></v-btn>
								<v-btn
									icon="mdi-delete"
									@click.stop="deleteRecipe(recipe.id)"
									aria-label="$t('recipe_list.delete')"
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
const pictureFilePath = import.meta.env.DEST_FILE_PATH;

export default {
	name: "recipesList",
	mounted() {
		const recipesStore = useRecipesStore();
		recipesStore.fetchRecipes().catch((err) => console.error(err));
	},
	computed: {
		recipesStore() {
			return useRecipesStore();
		},
		recipes() {
			return Array.isArray(this.recipesStore.recipes)
				? this.recipesStore.recipes
				: [];
		},
		sortedRecipes() {
			return this.recipes
				.filter((recipe) => recipe.name)
				.sort((a, b) => a.name.localeCompare(b.name));
		},
		imageUrl() {
			return (recipe) =>
				pictureFilePath + recipe.picture + "?t=" + new Date().getTime();
		},
	},
	methods: {
		async fetchRecipes() {
			try {
				await this.recipesStore.fetchRecipes();
			} catch (error) {
				console.error("Erreur lors de la récupération des recettes :", error);
			}
		},
		async deleteRecipe(recipeId) {
			try {
				await this.recipesStore.deleteRecipe(recipeId);
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
