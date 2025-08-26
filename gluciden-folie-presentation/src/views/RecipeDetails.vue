<template>
	<v-container fluid>
		<v-card class="mx-auto my-5 pa-4" max-width="800" elevation="4">
			<div v-if="recipe">
				<h1>{{ recipe.name }}</h1>
				<img
					v-if="recipe.picture"
					:src="recipePictureUrl"
					class="recipe-picture"
					alt="Recipe Picture"
					height="300px"
				/>

				<!-- Difficultés -->
				<h2 class="text-center">{{ $t("recipe_details.difficulty") }}</h2>
				<div class="d-flex justify-center align-center mb-4">
					<v-rating
						:model-value="difficultyIndex"
						readonly
						length="4"
						empty-icon="mdi-circle-outline"
						full-icon="mdi-circle"
						dense
						class="custom-rating"
					/>
				</div>

				<!-- Ingrédients -->
				<h2>{{ $t("recipe_details.ingredients") }}</h2>
				<v-chip-group column>
					<v-chip
						v-for="ingredient in recipe.ingredients"
						:key="ingredient.id"
						class="my-1"
						color="primary"
						text-color="white"
						outlined
					>
						{{ ingredient.ingredient.name }} - {{ ingredient.quantity }}
						{{ getUnityName(ingredient.unityId) }}
					</v-chip>
				</v-chip-group>

				<!-- Étapes -->
				<h2>{{ $t("recipe_details.steps") }}</h2>
				<v-card class="step-card" variant="outlined">
					<v-list>
						<v-list-item v-for="(step, index) in recipe.steps" :key="index">
							<v-list-item-content class="step-content">
								<v-list-item-title
									>{{ step.number }} . {{ step.description }}</v-list-item-title
								>
							</v-list-item-content>
						</v-list-item>
					</v-list>
				</v-card>
			</div>

			<div v-else>
				<p>{{ $t("recipe_details.recipe_download") }}</p>
			</div>

			<v-btn @click="$router.push({ name: 'recipesList' })" class="mt-4">
				{{ $t("recipe_details.return_button") }}
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
		difficultyIndex() {
			const levels = ["FACILE", "MOYEN", "DIFFICILE", "EXPERT"];
			if (!this.recipe || !this.recipe.difficulty) return 0;
			return levels.indexOf(this.recipe.difficulty.toUpperCase()) + 1;
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
	width: 100%; 
	aspect-ratio: 1 / 1; 
	object-fit: cover; 
	border-radius: 8px;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}

/* *** Card *** */
.step-card {
	border-radius: 12px;
	border: 2px solid #d3beb1;
}

.step-content {
	text-align: left;
}

/* *** Difficultés *** */
.custom-rating {
	color: #5d827f;
}

/* *** Chips *** */
.v-chip,
.v-list-item-title,
.v-text-field input {
	color: #5d827f;
}

.v-text-field {
	color: #5d827f;
}
</style>
