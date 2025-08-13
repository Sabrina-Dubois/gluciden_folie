<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("create_recipe.title") }}</h1>
		<v-card class="recipeForm" elevation="4" max-width="800px">
			<!-- Formulaire création -->
			<v-form @submit.prevent="addRecipe">
				<h3>{{ $t("create_recipe.recipe.name") }}</h3>
				<v-text-field
					v-model="form.name"
					:label="$t('create_recipe.recipe.name') + ' *'"
					:error="v$.form.name.$error"
					:error-messages="nameErrors"
					variant="underlined"
					hide-details="auto"
				></v-text-field>

				<h3>{{ $t("create_recipe.picture") }}</h3>

				<!-- Image existante -->
				<v-img
					v-if="imagePreview"
					:src="imagePreview"
					class="recipe-picture"
					height="200px"
					cover
					rounded=""
				></v-img>

				<!-- Nouvelle image -->
				<v-file-input
					@change="handleFileUpload"
					accept="image/png, image/jpeg"
					:label="$t('create_recipe.label') + ' *'"
					:error="v$.form.picture.$error"
					:error-messages="pictureErrors"
					class="required-field"
					chips
					prepend-icon="mdi-camera"
					variant="underlined"
				></v-file-input>

				<!-- Difficultés -->
				<h3>{{ $t("create_recipe.difficulty") }}</h3>
				<div class="custom-rating">
					<v-rating
						v-model="form.difficulty"
						class="required-field"
						:length="4"
						:error="v$.form.difficulty.$error"
						:error-messages="difficultyErrors"
						empty-icon="mdi-circle-outline"
						full-icon="mdi-circle"
						hover
						dense
						@update:modelValue="v$.form.difficulty.$touch()"
					></v-rating>
				</div>

				<!-- Ingrédients -->
				<h3>{{ $t("create_recipe.ingredients") }}</h3>
				<Ingredients v-model:ingredients="form.ingredients" />

				<!-- Étapes -->
				<div>
					<Steps v-model:steps="form.steps" />
				</div>

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
import Ingredients from "@/components/Ingredients.vue";
import Steps from "@/components/Steps.vue";
import i18n from "@/i18n/i18n";

export default {
	name: "createRecipe",
	components: {
		Ingredients,
		Steps,
	},
	data() {
		return {
			form: {
				name: "",
				picture: null,
				//ingredients: [],
				ingredientList: [
					{ ingredient: { name: "" }, quantity: null, unityId: null },
				],
				steps: [{ number: 1, description: "" }],
				difficulty: null,
				//steps: [],
			},
			imagePreview: null,
			v$: null,
			submitted: false,
		};
	},
	validations() {
		return {
			form: {
				...recipeValidation.form,
			},
		};
	},
	created() {
		this.v$ = useVuelidate();
	},

	computed: {
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
			if (!this.v$.form.difficulty.$error) {
				return [];
			}
			const errors = [];
			const rules = this.v$.form.difficulty;
			if (rules.required.$invalid)
				errors.push(i18n.global.t("validation.required"));
			return errors;
		},
	},

	methods: {
		handleFileUpload(event) {
			const file = event.target.files ? event.target.files[0] : null;
			if (file) {
				this.form.picture = file;
				this.imagePreview = URL.createObjectURL(file);
			} else {
				this.imagePreview = null;
				this.form.picture = null;
			}
		},

		difficultyNumberToString(num) {
			const difficultyLabels = ["FACILE", "MOYEN", "DIFFICILE", "EXPERT"];
			if (num && num >= 1 && num <= 4) {
				return difficultyLabels[num - 1];
			}
			return null;
		},

		async addRecipe() {
			this.submitted = true;

			await this.v$.$validate();

			if (this.v$.$invalid) {
				console.log(
					"Formulaire invalide",
					JSON.parse(JSON.stringify(this.v$.$errors))
				);
				return;
			}
			if (
				!Array.isArray(this.form.ingredients) ||
				this.form.ingredients.length === 0
			) {
				return;
			}

			try {
				const recipesStore = useRecipesStore();

				await recipesStore.addRecipe({
					name: this.form.name,
					picture: this.form.picture,
					difficulty: this.difficultyNumberToString(this.form.difficulty),
					ingredientList: this.form.ingredientList,
					steps: this.form.steps
				});
			} catch (error) {
				console.error("Erreur lors de la création de la recette :", error);
			}
			this.$router.push({ name: "recipesList" });
		},
	},
};
</script>

<style scoped>
.v-form {
	background-color: white;
	width: 100%;
	overflow-x: hidden;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}
.custom-btn {
	display: block;
	margin: 30px auto 0 auto;
	width: auto !important;
	min-width: 150px;
	padding: 10px 25px;
	font-weight: 600;
}

/* *** MOBILE FIRST *** */
.main-content.custom-bg {
	padding: 10px 15px 30px; /* un peu plus d’espace en bas */
}

.recipeForm {
	max-width: 100%;
	margin: 0 auto;
	padding: 15px;
	box-sizing: border-box;
}

/* *** Champs *** */
.v-text-field,
.v-file-input {
	width: 100% !important;
	margin-bottom: 20px;
	padding-left: 10px;
	color: #5d827f;
}

/* *** Titre principal *** */
h1 {
	font-size: 1.5rem;
	font-weight: 600;
	margin-bottom: 15px;
	text-align: center;
	word-break: break-word;
}
h3 {
	font-size: 1.2rem;
	margin-bottom: 10px;
	color: #5d827f;
	word-break: break-word;
}

/* *** Image *** */
.recipe-picture {
	width: 100% !important;
	height: auto !important;
	max-height: 200px;
	object-fit: contain !important;
	margin-bottom: 20px;
	border-radius: 8px;
}

/* *** Rating *** */
.custom-rating {
	color: #5d827f;
	margin-bottom: 20px;
}

.ingredients,
.steps,
.v-form > div {
	width: 100%;
	box-sizing: border-box;
}

/* *** DESKTOP *** */
@media (min-width: 900px) {
	.recipeForm {
		max-width: 800px;
	}

	h1 {
		font-size: 2.2rem;
	}
}
</style>
