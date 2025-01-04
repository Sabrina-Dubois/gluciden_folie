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
					:label="$t(`update_recipe.label`) + ' *'"
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
		this.initRecipe();
	},
	validations() {
		return recipeValidation;
	},
	created() {
		this.v$ = useVuelidate(); // Initialisation de Vuelidate
	},
	mounted() {
		this.initRecipe();
	},
	methods: {
		async initRecipe() {
			try {
				const response = await apiClient.get(`/recipes/${this.id}`);
				this.recipeName = response.data.name;
				this.imagePreview = `/images/${response.data.picture}`; // Afficher l'image existante
			} catch (error) {
				console.error("Erreur lors de la récupération de la recette:", error);
			}
		},
		// Gestion du téléchargement de l'image
		handleFileUpload(event) {
			const file = event.target.files[0];
			if (file) {
				this.recipePicture = file;
				this.imagePreview = URL.createObjectURL(file); // Afficher l'image téléchargée
			}
		},
		// Mise à jour de la recette
		async updateRecipe() {
			this.submitted = true; // formulaire soumis
			this.v$.$touch(); // Marque tous les champs comme touchés
			if (this.v$.$invalid) {
				console.log("Formulaire invalide");
				return; // Si le formulaire est invalide, arrête la soumission
			}
			const formData = new FormData();
			formData.append("name", this.recipeName);

			if (this.recipePicture) {
				formData.append("picture", this.recipePicture);
			}

			try {
				const response = await apiClient.put(`/recipes/${this.id}`, formData, {
					headers: {
						"Content-Type": "multipart/form-data",
					},
				});

				if (response.status === 200) {
					this.submitted = false;
					console.log("Recette mise à jour avec succès !");
					this.$router.push({ name: "recipesList" });

					if (this.recipePicture) {
						this.imagePreview = URL.createObjectURL(this.recipePicture);
					}
				} else {
					console.error("Erreur lors de la mise à jour de la recette");
				}
			} catch (error) {
				if (error.response) {
					console.error(
						"Erreur réseau lors de la mise à jour:",
						error.response.data
					);
				} else {
					console.error("Erreur réseau lors de la mise à jour:", error.message);
				}
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

.v-btn {
	background-color: #5d827f;
	color: #d3beb1;
}

.recipe-picture {
	margin-top: 20px;
}
</style>
