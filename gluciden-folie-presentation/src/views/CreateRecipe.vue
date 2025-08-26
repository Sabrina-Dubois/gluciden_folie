<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("create_recipe.title") }}</h1>
		<v-card class="recipeForm" elevation="4" max-width="800px">
			<!-- Formulaire création -->
			<v-form @submit.prevent="addRecipe">
				<h2>{{ $t("create_recipe.recipe.name") }}</h2>
				<v-text-field
					v-model="form.name"
					:label="$t('create_recipe.recipe.name') + ' *'"
					:error="v$.form.name.$error"
					:error-messages="nameErrors"
					variant="underlined"
					hide-details="auto"
				></v-text-field>

				<h2>{{ $t("create_recipe.picture") }}</h2>

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
					v-model="form.picture"
					@change="handleFileUpload"
					accept="image/png, image/jpeg"
					:label="$t('create_recipe.label') + ' *'"
					:aria-label="$t('create_recipe.label')"
					:error="v$.form.picture.$error"
					:error-messages="pictureErrors"
					class="required-field"
					chips
					prepend-icon="mdi-camera"
					variant="underlined"
					:multiple="false"
				></v-file-input>

				<!-- Difficultés -->
				<h2>{{ $t("create_recipe.difficulty") }}</h2>
				<div class="custom-rating">
					<v-rating
						v-model="form.difficulty"
						class="required-field"
						:length="4"
						:aria-label="$t('create_recipe.difficulty')"
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
				<h2>{{ $t("create_recipe.ingredients") }}</h2>
				<Ingredients v-model:ingredients="form.ingredientList" />

				<!-- Étapes -->
				<div>
					<Steps v-model:steps="form.steps" />
				</div>

				<v-btn
					class="custom-btn"
					ml-5
					rounded=""
					type="submit"
					:aria-label="$t('create_recipe.button')"
				>
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
	components: { Ingredients, Steps },
	data() {
		return {
			form: {
				name: "",
				picture: null,
				ingredientList: [
				],
				steps: [{ number: 1, description: "" }],
				difficulty: null,
			},
			imagePreview: null,
			v$: null,
			submitted: false,
		};
	},
	validations() {
		return { form: { ...recipeValidation.form } };
	},
	created() {
		this.v$ = useVuelidate();
	},

	computed: {
		nameErrors() {
			const errors = [];
			const rules = this.v$.form.name;
			if (rules.$error) {
				if (rules.required.$invalid)
					errors.push(i18n.global.t("validation.required"));
				if (rules.minLength.$invalid)
					errors.push(i18n.global.t("validation.minLength", { min: 4 }));
				if (rules.maxLength.$invalid)
					errors.push(i18n.global.t("validation.maxLength", { max: 100 }));
			}
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
			const errors = [];
			const rules = this.v$.form.difficulty;
			if (rules.$error && rules.required.$invalid)
				errors.push(i18n.global.t("validation.required"));
			return errors;
		},
	},

	methods: {
		handleFileUpload(files) {
			if (files && files.length > 0) {
				const url = URL.createObjectURL(files[0]);
			}
		},

		difficultyNumberToString(num) {
			const difficultyLabels = ["FACILE", "MOYEN", "DIFFICILE", "EXPERT"];
			return num >= 1 && num <= 4 ? difficultyLabels[num - 1] : null;
		},

		async addRecipe() {
			this.submitted = true;
			this.v$.$touch();
			await this.v$.$validate();

			if (this.v$.$invalid) {
				return;
			}

			if (
				!Array.isArray(this.form.ingredientList) ||
				!this.form.ingredientList.length
			)
				return;

			try {
				const recipesStore = useRecipesStore();
				await recipesStore.addRecipe({
					name: this.form.name,
					picture: this.form.picture,
					difficulty: this.difficultyNumberToString(this.form.difficulty),
					ingredientList: this.form.ingredientList,
					steps: this.form.steps,
				});
				this.$router.push({ name: "recipesList" });
			} catch (error) {
				console.error("Erreur lors de la création de la recette :", error);
			}
		},
	},
};
</script>


<style scoped>
/* *** Formulaire *** */
.v-form {
	background-color: white;
	width: 100%;
	overflow-x: hidden;
}
.recipeForm {
	max-width: 100%;
	margin: 0 auto;
	padding: 15px;
	box-sizing: border-box;
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
	padding: 10px 15px 30px;
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
h2 {
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
	object-fit: contai !important;
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
ƒ