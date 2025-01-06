<template>
	<v-container class="recipe-details-container" fluid>
		<v-card class="d-flex mx-auto my-auto">
			<h1 v-if="recipe?.name">{{ recipe.name }}</h1>

			<v-img
				:key="recipe?.picture + recipe?.id"
				:src="recipeImageUrl"
				class="recipe-image"
				height="300px"
				contain
			></v-img>
			<p>Portions </p>
			<p>Temps de préparation </p>
			<p>Temps de cuisson </p>
			<h3>Ingrédients :</h3>
			
			<h3>Instructions :</h3>
			<v-btn
				class="custom-btn"
				ml-5
				rounded=""
				@click="$router.push({ name: 'recipesList' })"
				>Retour à la liste</v-btn
			>
		</v-card>
	</v-container>
</template>

<script>
import apiClient from "@/api/axiosConfig";

export default {
	name: "recipeDetails",
	props: ["id"],
	data() {
		return {
			recipe: null,
		};
	},

	computed: {
		recipeImageUrl() {
			if (this.recipe && this.recipe.picture) {
				return `/images/${this.recipe.picture}?t=${new Date().getTime()}`;
			}
			return "";
		},
	},
	async created() {
		try {
			const response = await apiClient.get(`/recipes/${this.id}`);
			this.recipe = { ...response.data };
			console.log("hello: ", this.recipe);
		} catch (error) {
			console.error("Erreur lors du chargement de la recette :", error);
		}
	},
};
</script>

<style scoped>
.recipe-details {
	max-width: 800px;
	margin: auto;
	padding: 20px;
}
.recipe-image {
	margin: 20px 0;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}
</style>
