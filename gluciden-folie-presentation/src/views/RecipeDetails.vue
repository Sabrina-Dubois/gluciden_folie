<template>
	<v-container fluid>
		<v-card class="mx-auto my-5 pa-4" max-width="600">
			<div v-if="recipe">
				<h1>{{ recipe.name }}</h1>
				<img
					v-if="recipe.picture"
					:src="recipePictureUrl"
					class="recipe-picture"
					alt="Recipe Picture"
					height="300px"
				/>

				<h3>Ingrédients :</h3>
				<v-chip-group column>
					<v-chip
						v-for="ingredient in recipe.ingredients"
						:key="ingredient.id"
						class="my-1"
						color="primary"
						text-color="white"
						outlined
					>
						{{ ingredient.name }} - {{ ingredient.quantity }}
						{{ getUnityName(ingredient.unityId) }}
					</v-chip>
				</v-chip-group>
			</div>

			<div v-else>
				<p>Chargement de la recette...</p>
			</div>

			<v-btn @click="$router.push({ name: 'recipesList' })" class="mt-4">
				Retour à la liste
			</v-btn>
		</v-card>
	</v-container>
</template>

<script>
import apiClient from "@/api/axiosConfig";

export default {
	name: "recipeDetails",
	data() {
		return {
			recipe: null,
			unitiesList: [],
		};
	},
	computed: {
		recipePictureUrl() {
			if (this.recipe && this.recipe.picture) {
				return `/images/${this.recipe.picture}?t=${Date.now()}`;
			}
			return "";
		},
	},
	async mounted() {
		const id = this.$route.params.id;
		await this.fetchUnities(); 

		try {
			const response = await apiClient.get(`/recipes/${id}`);
			this.recipe = response.data;
		} catch (error) {
			console.error("❌ Erreur lors du chargement de la recette :", error);
		}
	},
	methods: {
		getUnityName(unityId) {
			const unity = this.unitiesList.find((u) => u.id === unityId);
			return unity ? unity.name : "";
		},
		async fetchUnities() {
			try {
				const response = await apiClient.get("/unities");
				this.unitiesList = response.data;
			} catch (error) {
				console.error("Erreur lors du chargement des unités :", error);
			}
		},
	},
};
</script>


<style scoped>
.recipe-details {
	max-width: 800px;
	margin: auto;
	padding: 20px;
}
.recipe-picture {
	margin: 20px 0;
	object-fit: cover;
	border-radius: 8px;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}

/* *** Chips *** */
.v-chip,
.v-text-field input {
	color: #5d827f;
}

.v-text-field {
	color: #5d827f;
}
</style>
