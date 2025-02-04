<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("update_recipe.title") }}</h1>
		<v-card class="recipeForm d-flex align-center">
			<!-- Formulaire pour modifier une recette -->
			<v-form @submit.prevent="updateRecipe">
				<h3>{{ $t("update_recipe.title") }}</h3>
				<v-text-field
					v-model="recipeName"
					:label="$t(`update_recipe.recipe.label`) + ' *'"
					:error="v$.recipeName.$error"
					:error-messages="getRecipeNameErrorMessages()"
					hide-details="auto"
					variant="underlined"
				></v-text-field>

				<h3>{{ $t("update_recipe.picture.picture") }}</h3>

				<!-- Afficher l'image existante ou nouvelle -->
				<v-img
					v-if="imagePreview"
					:src="imagePreview"
					class="recipe-picture"
					height="200px"
					cover
					rounded=""
				></v-img>

				<!-- Champs nouvelle image -->
				<v-file-input
					@change="handleFileUpload"
					accept="image/*"
					:label="$t(`update_recipe.picture.label`) + ' *'"
					:error="v$.recipePicture.$error"
					:error-messages="getRecipePictureErrorMessages()"
					chips
					variant="underlined"
				></v-file-input>

				<v-btn
					class="custom-btn"
					ml-5
					rounded=""
					@click="updateRecipe"
					type="submit"
					>{{ $t("update_recipe.button") }}</v-btn
				>
			</v-form>
		</v-card>
	</div>
</template>

<script>
import apiClient from "../api/axiosConfig";
import { useRecipesStore } from "@/stores/recipesStore.js";
import { recipeValidation } from "../utils/validationRules.js";
import useVuelidate from "@vuelidate/core";
import { messages } from "../utils/validationMessages.js";

export default {
	name: "updateRecipe",
	data() {
		return {
			id: this.$route.params.id, // Récupération de l'ID de la recette
			recipeName: "",
			recipePicture: "",
			imagePreview: "",
			v$: null,
			submitted: false,
		};
	},
	beforeMount() {
		this.loadRecipe();
	},
	validations() {
		return recipeValidation;
	},
	created() {
		this.v$ = useVuelidate(); // Initialisation de Vuelidate
	},
	mounted() {
		this.loadRecipe();
	},
	methods: {
		async loadRecipe() {
			try {
				const response = await apiClient.get(`/recipes/${this.id}`);
				this.recipeName = response.data.name;
				this.recipePicture = response.data.picture;
				this.imagePreview = `/images/${response.data.picture}`; // Afficher l'image existante
			} catch (error) {
				console.error("Erreur lors de la récupération de la recette:", error);
			}
		},
		// Gestion du téléchargement de l'image
		handleFileUpload(event) {
			const file = event.target.files[0];
			if (file) {
				this.recipePicture = file; // Cette ligne garde la référence du fichier
				this.imagePreview = URL.createObjectURL(file); // Crée une URL pour la prévisualisation
			} else {
				// Si aucun fichier n'est sélectionné, on peut réinitialiser l'image
				this.imagePreview = "";
				this.recipePicture = "";
			}
		},
		// Mise à jour de la recette
		async updateRecipe() {
			this.submitted = true; // Marque comme soumis
			this.v$.$touch(); // Marquer tous les champs comme touchés
			if (this.v$.$invalid) {
				console.log("Formulaire invalide");
				return;
			}

			const formData = new FormData(); // S'assurer que formData est bien initialisé ici
			formData.append("name", this.recipeName);

			// Si une nouvelle image a été téléchargée, ajouter la nouvelle image
			if (this.recipePicture && this.recipePicture instanceof File) {
				// Si la nouvelle image est un fichier
				formData.append("picture", this.recipePicture);
			} else if (this.recipePicture && typeof this.recipePicture === "string") {
				// Si la recette a une image existante (URL)
				formData.append("picture", this.recipePicture);
			} else {
				// Si aucune image n'est spécifiée, conserver une valeur vide ou une image par défaut
				formData.append("picture", this.imagePreview || "");
			}

			const recipeStore = useRecipesStore(); // Utilisation du store Pinia
			const success = await recipeStore.updateRecipe(
				this.id,
				this.recipeName,
				formData
			); // Appel du store Pinia
			if (success) {
				this.$router.push({ name: "recipesList" });
			} else {
				console.error("Erreur lors de la mise à jour de la recette");
			}
		},
		getRecipeNameErrorMessages() {
			const errors = [];
			console.log("Validation status:", this.v$.recipeName);

			if (this.v$.recipeName.$error) {
				if (this.v$.recipeName.required.$invalid) {
					errors.push(messages.required);
				}
				if (this.v$.recipeName.minLength.$invalid) {
					errors.push(messages.minLength(4));
				}
				if (this.v$.recipeName.maxLength.$invalid) {
					errors.push(messages.maxLength(100));
				}
			}
			return errors;
		},
		getRecipePictureErrorMessages() {
			const errors = [];

			if (this.v$.recipePicture.$error) {
				if (this.v$.recipePicture.required.$invalid) {
					errors.push(messages.required);
				}
				if (this.v$.recipePicture.validImageType.$invalid) {
					errors.push(messages.validImageType);
				}
				if (this.v$.recipePicture.validImageSize.$invalid) {
					errors.push(messages.validImageSize);
				}
			}
			return errors;
		},
	},
};
</script>

<style scoped>
/* *** Boutons *** */
.v-btn {
	justify-items: center;
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
.v-file-input {
	margin-bottom: 20px;
	color: #5d827f;
}

.recipe-picture {
	margin-top: 20px;
}
</style>
