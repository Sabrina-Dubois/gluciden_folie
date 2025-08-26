<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("update_recipe.title") }}</h1>
		<v-card class="recipeForm d-flex align-center" elevation="4">
			<v-form @submit.prevent="updateRecipe" class="form-content">
				<!-- Nom de la recette -->
				<v-text-field
					v-model="form.name"
					:label="$t('update_recipe.recipe.label') + ' *'"
					:error="v$.form.name.$error"
					:error-messages="nameErrors"
					variant="underlined"
					hide-details="auto"
					dense
					outlined
					required
					aria-required="true"
				/>

				<!-- Image actuelle -->
				<v-img
					v-if="imagePreview"
					:src="imagePreview"
					:alt="form.name ? form.name : $t('update_recipe.picture.alt')"
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
					dense
					outlined
					required
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
					dense
					outlined
					required
				/>

				<!-- Ingrédients -->
				<Ingredients v-model:ingredients="form.ingredients" />

				<!-- Étapes -->
				<Steps
					:steps="form.steps"
					@update:steps="
						(val) => {
							form.steps = val;
						}
					"
				/>

				<!-- Bouton de validation -->
				<v-btn
					class="custom-btn"
					type="submit"
					large
					:aria-label="$t('update_recipe.button')"
				>
					{{ $t("update_recipe.button") }}
				</v-btn>
			</v-form>
		</v-card>
	</div>
</template>

<script>
// (Pas de changement sur le script, on garde pareil)
import apiClient from "../api/axiosConfig";
import { useRecipesStore } from "@/stores/recipesStore.js";
import { recipeValidation } from "../utils/validationRules.js";
import useVuelidate from "@vuelidate/core";
import i18n from "@/i18n/i18n";
import Ingredients from "@/components/Ingredients.vue";
import Steps from "@/components/Steps.vue";

export default {
	name: "updateRecipe",
	components: { Ingredients, Steps },
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
			imagePreview: "",
			v$: null,
			submitted: false,
		};
	},
	created() {
		this.v$ = useVuelidate();
	},
	mounted() {
		this.fetchRecipe();
	},
	validations() {
		return recipeValidation;
	},
	computed: {
		nameErrors() {
			if (!this.v$.form.name.$error) return [];
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
				if (rules.required.$invalid)
					errors.push(i18n.global.t("validation.required"));
				if (rules.validImageType.$invalid)
					errors.push(i18n.global.t("validation.validImageType"));
				if (rules.validImageSize.$invalid)
					errors.push(i18n.global.t("validation.validImageSize"));
			}
			return errors;
		},
		difficultyErrors() {
			if (!this.v$.form.difficulty.$error) return [];
			const errors = [];
			const rules = this.v$.form.difficulty;
			if (rules.required.$invalid)
				errors.push(i18n.global.t("validation.required"));
			return errors;
		},
	},
	methods: {
		async fetchRecipe() {
			try {
				const response = await apiClient.get(`/recipes/${this.form.id}`);
				const data = response.data;
				this.form.name = data.name;
				this.form.picture = data.picture;
				this.form.difficulty = data.difficulty || "";
				this.form.ingredients = (data.ingredients || []).map((ingredient) => ({
					ingredient: { name: ingredient.name, id: ingredient.id },
					quantity: ingredient.quantity,
					unityId: ingredient.unityId,
				}));
				this.form.steps = (data.steps || []).map((s, i) => ({
					number: i + 1,
					description: s.description || "",
				}));
				this.imagePreview = `/images/${data.picture}`;
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
				if (typeof this.form.picture !== "string" || !this.form.picture) {
					this.form.picture = this.imagePreview ? this.form.picture : "";
				}
				this.imagePreview = this.form.picture
					? `/images/${this.form.picture}`
					: "";
			}
		},
		async updateRecipe() {
			this.submitted = true;
			const isValid = await this.v$.$validate();
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
				formData.append("picture", this.form.picture);
			} else {
				formData.append("picture", "");
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
/* *** MOBILE FIRST *** */

/* *** Container *** */
.main-content.custom-bg {
	padding: 15px 10px 30px;
	box-sizing: border-box;
}

/* *** Titre *** */
h1 {
	font-size: 1.5rem;
	font-weight: 700;
	margin-bottom: 20px;
	white-space: normal;
	word-break: break-word;
	overflow-wrap: break-word;
}

/* *** Card *** */
.recipeForm {
	width: 100%;
	max-width: 100%;
	margin: 0 auto 20px;
	padding: 15px 10px;
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
	align-items: stretch;
	overflow-x: hidden;
}

/* *** Formulaire *** */
.form-content {
	display: flex;
	flex-direction: column;
	width: 100%;
	box-sizing: border-box;
	overflow-x: hidden;
}

/* *** Champs *** */
.v-text-field,
.v-file-input,
.v-select {
	margin-bottom: 20px;
	color: #5d827f;
	width: 100%;
	min-width: 0;
	box-sizing: border-box;
	padding-left: 10px;
	overflow-wrap: break-word;
	word-wrap: break-word;
}

/* *** Image *** */
.recipe-picture {
	margin-top: 15px;
	width: 100%;
	max-width: 100%;
	height: auto !important;
	object-fit: contain;
	border-radius: 6px;
	overflow: hidden;
	box-sizing: border-box;
}

/* *** Bouton *** */
.v-btn.custom-btn {
	background-color: #5d827f;
	color: #d3beb1;
	padding: 12px 30px;
	font-weight: 600;
	margin: 20px auto 0 auto;
	box-sizing: border-box;
	display: block;
	min-width: unset;
	width: auto;
	max-width: 100%;
	text-align: center;
}

.d-flex {
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
}

Ingredients,
Steps {
	max-width: 100%;
	overflow-wrap: break-word;
	word-wrap: break-word;
	box-sizing: border-box;
	width: 100%;
}

/* *** DESKTOP *** */
@media (min-width: 900px) {
	.recipeForm {
		max-width: 800px;
		padding: 25px 40px;
	}

	.v-text-field,
	.v-file-input,
	.v-select {
		margin-bottom: 30px;
	}

	.v-btn.custom-btn {
		padding: 12px 50px;
	}
}
</style>
