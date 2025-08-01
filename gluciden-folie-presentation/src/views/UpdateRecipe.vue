<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("update_recipe.title") }}</h1>
		<v-card class="recipeForm d-flex align-center" elevation="4">
			<v-form @submit.prevent="updateRecipe">
				<!-- Nom de la recette -->
				<v-text-field
					v-model="form.name"
					:label="$t('update_recipe.recipe.label') + ' *'"
					:error="v$.form.name.$error"
					:error-messages="nameErrors"
					variant="underlined"
					hide-details="auto"
				/>
				<pre>{{ JSON.stringify(form.ingredients, null, 2) }}</pre>

				<!-- Image actuelle -->
				<h3>{{ $t("update_recipe.picture.picture") }}</h3>
				<v-img
					v-if="imagePreview"
					:src="imagePreview"
					class="recipe-picture"
					height="200px"
					cover
				/>

				<!-- Nouvelle image -->
				<v-file-input
					@change="handleFileUpload"
					accept="image/*"
					:label="$t('update_recipe.picture.label') + ' *'"
					:error="v$.form.picture.$error"
					:error-messages="pictureErrors"
					chips
					variant="underlined"
				/>

				<!-- Difficulté -->
				<v-select
					v-model="form.difficulty"
					:items="['FACILE', 'MOYEN', 'DIFFICILE', 'EXPERT']"
					:label="$t('update_recipe.difficulty.label')"
					:error="v$.form.difficulty.$error"
					:error-messages="difficultyErrors"
					variant="underlined"
					hide-details="auto"
				/>

				<!-- Ingrédients -->
				<Ingredients v-model:ingredients="form.ingredients" />

				<!-- Etape -->
				
				<Steps
					:steps="form.steps"
					@update:steps="
						(val) => {
							console.log('Steps updated', val);
							form.steps = val;
						}
					"
				/>

				<!-- Bouton de validation -->
				<v-btn class="custom-btn" type="submit">
					{{ $t("update_recipe.button") }}
				</v-btn>
			</v-form>
		</v-card>
	</div>
</template>

<script>
import apiClient from "../api/axiosConfig";
import { useRecipesStore } from "@/stores/recipesStore.js";
import { recipeValidation } from "../utils/validationRules.js";
import useVuelidate from "@vuelidate/core";
import i18n from "@/i18n/i18n"; 
//import { messages } from "../utils/validationMessages.js";
import Ingredients from "@/components/Ingredients.vue";
import Steps from "@/components/Steps.vue";

export default {
	name: "updateRecipe",
	components: {
		Ingredients,
		Steps,
	},
	data() {
		return {
			form: {
				id: this.$route.params.id,
				name: "",
				picture: "",
				ingredients: [],
				difficulty: null,
				steps: [],
			},

			formLoaded: false,
			ingredients: [],
			imagePreview: "",
			newIngredient: "",
			v$: null,
			submitted: false,
		};
	},
	mounted() {
		this.fetchRecipe();
	},
	validations() {
		return recipeValidation;
	},
	created() {
		this.v$ = useVuelidate();
		//this.fetchRecipe();
	},
	computed: {
		formattedIngredients() {
			return this.ingredients.map((i) => {
				return {
					ingredient:
						typeof i.ingredient === "object"
							? i.ingredient
							: { name: i.name || "???" },
					quantity: i.quantity,
					unityId: i.unityId,
				};
			});
		},
		nameErrors() {
			if (!this.v$.form.name.$error) {
				return [];
			}
			const errors = [];
			const rules = this.v$.form.name;
			if (rules.required.$invalid)
				errors.push(i18n.global.t("validation.required"));
			if (rules.minLength.$invalid)
				errors.push(i18n.global.t("validation.minLength", { min: 4 }));
			if (rules.maxLength.$invalid)
				errors.push(i18n.global.t("validation.maxLength", { max: 100 }));
			return errors;
		},
		pictureErrors() {
			const errors = [];
			const rules = this.v$.form.picture;

			if (rules.$error) {
				if (rules.required.$invalid) errors.push(i18n.global.t("validation.required"));
				if (rules.validImageType.$invalid) errors.push(i18n.global.t("validation.validImageType"));
				if (rules.validImageSize.$invalid) errors.push(i18n.global.t("validation.validImageSize"));
			}

			return errors;
		},
		difficultyErrors() {
			if (!this.v$.form.difficulty.$error) {
				return [];
			}
			const errors = [];
			const rules = this.v$.form.difficulty;
			if (rules.required.$invalid) errors.push(i18n.global.t("validation.required"));
			return errors;
		},
	},
	watch: {
		ingredients: {
			immediate: true,
			handler(val) {
				console.log("🧪 INGREDIENTS:", val);
			},
		},
	},
	methods: {
		async fetchRecipe() {
			try {
				const response = await apiClient.get(`/recipes/${this.form.id}`);
				console.log("Recette complète reçue :", response.data);
				this.form.name = response.data.name;
				this.form.picture = response.data.picture;
				this.form.difficulty = response.data.difficulty || "";
				this.form.ingredients = (response.data.ingredients || []).map(
					(ingredient) => ({
						ingredient: { name: ingredient.name, id: ingredient.id },
						quantity: ingredient.quantity, // Quantité utilisée
						unityId: ingredient.unityId, // ID de l’unité (pas l’objet entier)
					})
				);
				this.form.steps = (response.data.steps || []).map((s, i) => ({
					number: i + 1,
					description: s.description ?? "",
				}));

				this.imagePreview = `/images/${response.data.picture}`;
				console.log("Form après chargement:", this.form);
			} catch (error) {
				console.error("Erreur lors de la récupération de la recette:", error);
			}
		},
		handleFileUpload(event) {
			const file = event?.target?.files?.[0];
			if (file) {
				this.form.picture = file;
				this.imagePreview = URL.createObjectURL(file);
			} else {
				// Pas de nouveau fichier sélectionné → garder l'ancienne image string
				if (typeof this.form.picture !== "string" || !this.form.picture) {
					// Si c'était un File ou vide, on remet l'ancienne image string
					this.form.picture = this.imagePreview ? this.form.picture : "";
				}
				this.imagePreview = this.form.picture
					? `/images/${this.form.picture}`
					: "";
			}
		},
		addIngredient() {
			const trimmed = this.newIngredient.trim();
			if (trimmed && !this.ingredients.includes(trimmed)) {
				this.ingredients.push(trimmed);
			}
			this.newIngredient = "";
		},
		removeIngredient(index) {
			this.ingredients.splice(index, 1);
		},
		async updateRecipe() {
			this.submitted = true;
			const isValid = await this.v$.$validate();
			console.log("Validation OK ?", isValid);
			if (!isValid) return;

			const formData = new FormData();
			formData.append("name", this.form.name);
			formData.append("difficulty", this.form.difficulty);
			formData.append("ingredients", JSON.stringify(this.form.ingredients));
			formData.append("steps", JSON.stringify(this.form.steps));

			if (this.form.picture instanceof File) {
				formData.append("picture", this.form.picture);
			} else if (
				typeof this.form.picture === "string" &&
				this.form.picture.trim() !== ""
			) {
				formData.append("picture", this.form.picture); // On garde l'ancienne image
			} else {
				formData.append("picture", ""); // Pas d'image
			}

			const recipeStore = useRecipesStore();
			const success = await recipeStore.updateRecipe(this.form.id, formData);
			if (success) {
				this.$router.push({ name: "recipesList" });
			} else {
				console.error("Erreur lors de la mise à jour");
			}
		},
	},
};
</script>

<style scoped>
.v-btn {
	background-color: #5d827f;
	color: #d3beb1;
}
.main-content.custom-bg {
	padding-top: 10px;
}
.d-flex {
	align-items: center;
	justify-content: center;
}
.recipeForm {
	max-width: 900px;
	margin: auto;
}
.v-text-field,
.v-file-input,
.v-select {
	margin-bottom: 20px;
	color: #5d827f;
}
.recipe-picture {
	margin-top: 20px;
}
</style>
