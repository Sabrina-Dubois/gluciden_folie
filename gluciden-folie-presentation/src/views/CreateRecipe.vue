<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("create_recipe.title") }}</h1>
		<v-card class="recipeForm d-flex align-center">
			<!-- Formulaire pour créer une recette -->
			<v-form @submit.prevent="addRecipe" :model="v$">
				<h3>{{ $t("create_recipe.recipe.name") }}</h3>
				<v-text-field
					v-model="recipeName"
					:label="$t('create_recipe.recipe.name') + ' *'"
					:error="v$.recipeName.$error"
					:error-messages="recipeNameErrors"
					variant="underlined"
					hide-details="auto"
				></v-text-field>

				<h3>{{ $t("create_recipe.picture") }}</h3>

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
					v-model="recipePicture"
					@change="handleFileUpload"
					accept="image/png, image/jpeg"
					:label="$t('create_recipe.label') + ' *'"
					:error="v$.recipePicture.$error"
					:error-messages="recipePictureErrors"
					class="required-field"
					chips
					prepend-icon="mdi-camera"
					variant="underlined"
				></v-file-input>

				<v-btn class="custom-btn" ml-5 rounded="" type="submit">
					{{ $t("create_recipe.button") }}
				</v-btn>
			</v-form>
		</v-card>
	</div>
</template>

<script>
import { useRecipesStore } from "@/stores/recipesStore.js";
import { recipeValidation } from "../utils/validationRules.js";
import useVuelidate from "@vuelidate/core";
import { messages } from "../utils/validationMessages.js";

export default {
	name: "createRecipe",
	data() {
		return {
			recipeName: "",
			recipePicture: null,
			imagePreview: null,
			v$: null,
			submitted: false,
		};
	},
	validations() {
		return recipeValidation;
	},
	created() {
		this.v$ = useVuelidate();
	},
	
	computed: {
		recipeNameErrors() {
			if (!this.v$.recipeName.$error) {
				return [];
			}
			const errors = [];
			const rules = this.v$.recipeName;
			if (rules.required.$invalid) errors.push(messages.required);
			if (rules.minLength.$invalid) errors.push(messages.minLength(4));
			if (rules.maxLength.$invalid) errors.push(messages.maxLength(100));
			return errors;
		},
		recipePictureErrors() {
			const errors = [];
			const rules = this.v$.recipePicture;

			if (rules.$error) {
				if (rules.required.$invalid) errors.push(messages.required);
				if (rules.validImageType.$invalid) errors.push(messages.validImageType);
				if (rules.validImageSize.$invalid) errors.push(messages.validImageSize);
			}

			return errors;
		},
	},

	methods: {
		handleFileUpload(event) {
			const file = event.target.files[0];
			if (file) {
				this.recipePicture = file;
				this.imagePreview = URL.createObjectURL(file);
			}
		},
		
		async addRecipe() {
			this.submitted = true; // formulaire soumis
			this.v$.$touch(); // Marque tous les champs comme touchés
			if (this.v$.$invalid) {
				console.log("Formulaire invalide");
				return;
			}
			try {
				const recipesStore = useRecipesStore();
				const response = await recipesStore.addRecipe(this.recipeName, this.recipePicture);

				if (response) {
					this.recipeName = "";
					this.recipePicture = null;
					this.imagePreview = null;
					this.submitted = false;
					this.$router.push({ name: "recipesList" });
					} else {
					console.error("Erreur lors de la création de la recette");
				}
			} catch (error) {
				console.error("Erreur lors de la création de la recette :", error);
			}
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

.v-icon,
.v-rating {
	color: #f29eb0;
}

.recipeForm {
	max-width: 900px;
	margin: auto;
}

.v-form {
	background-color: white;
}

.v-text-field,
.v-file-input {
	max-height: 20px;
	max-width: auto;
	margin-bottom: 90px;
	padding-left: 20px;
	color: #5d827f;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}

.v-rating__wrapper span {
	margin: 0 10px;
}
</style>
