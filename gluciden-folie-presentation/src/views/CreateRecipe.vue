<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("create_recipe.title") }}</h1>
		<v-card class="recipeForm" max-width="800px">
			{{ form }}
			<!-- Formulaire pour créer une recette -->
			<v-form @submit.prevent="addRecipe" :model="v$">
				<h3>{{ $t("create_recipe.recipe.name") }}</h3>
				<v-text-field
					v-model="form.recipeName"
					:label="$t('create_recipe.recipe.name') + ' *'"
					:error="v$.form.recipeName.$error"
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
					v-model="form.recipePicture"
					@change="handleFileUpload"
					accept="image/png, image/jpeg"
					:label="$t('create_recipe.label') + ' *'"
					:error="v$.form.recipePicture.$error"
					:error-messages="recipePictureErrors"
					class="required-field"
					chips
					prepend-icon="mdi-camera"
					variant="underlined"
					></v-file-input>

				<!-- Nombre de portions -->
				<!-- <h3 class="section-title portions-title"> -->
				<!-- Nombre de portion{{ $t("create_recipe.portions") }}
				</h3>
				<v-text-field
					v-model="numberOfPortions"
					type="number"
					label="Nombre de portions"
					hide-details
					variant="underlined"
				></v-text-field> -->

				<!-- <h3>Temps de préparation</h3>
				<v-container>
					<v-row>
						<v-text-field
							class="timeCook"
							v-model="hourTime"
							label="Heures"
							hide-details
							variant="underlined"
						></v-text-field>
						<v-text> : </v-text>
						<v-text-field
							class="timeCook"
							v-model="minuteTime"
							label="Minutes"
							hide-details
							variant="underlined"
						></v-text-field>
					</v-row>
				</v-container> -->
				<!-- <h3>Temps de cuisson</h3>
				<v-container>
					<v-row>
						<v-text-field
							class="timeCook"
							v-model="hourTime"
							label="Heures"
							hide-details
							variant="underlined"
						></v-text-field>
						<v-text> : </v-text>
						<v-text-field
							class="timeCook"
							v-model="minuteTime"
							label="Minutes"
							hide-details
							variant="underlined"
						></v-text-field>
					</v-row>
				</v-container> -->

				<!-- <h3 class="text-h6 mb-2">Type de cuisson</h3>
				<v-container class="mx-auto" max-width="400">
					<v-chip-group v-model="amenities" column multiple>
						<v-chip text="Sans cuisson" variant="outlined" filter></v-chip>
						<v-chip text="Micro-onde" variant="outlined" filter></v-chip>
						<v-chip text="Four" variant="outlined" filter></v-chip>
						<v-chip text="Poêle" variant="outlined" filter></v-chip>
						<v-chip text="Vapeur" variant="outlined" filter></v-chip>
					</v-chip-group>
				</v-container> -->

				<!-- <v-container class="boo pa-0" fluid>
					<v-row class="d-flex">
						<v-col
							v-for="(cook, index) in cooks"
							:key="index"
							cols="12"
							md="3"
							no-gutters
							@click="selectCook(index)"
						>
							<v-card>
								<v-icon v-if="cook.icon">
									{{ cook.icon }}
								</v-icon>
								<v-card-subtitle class="text-center">
									{{ cook.name }}
								</v-card-subtitle>
							</v-card>
						</v-col>
					</v-row>
				</v-container> -->

				<!-- <h3>Difficultés</h3>
				<div class="text-center">
					<v-rating
						v-model="rating"
						:item-labels="labelsDifficulty"
						length="4"
						empty-icon="mdi-circle-outline"
						full-icon="mdi-circle"
						hover
					>
						<template v-slot:item-label="props">
							<span>
								{{ props.label }}
							</span>
						</template>
					</v-rating>
				</div> -->

				<!-- <h3>Coûts</h3>
				<div class="text-center">
					<v-rating
						v-model="rating"
						:item-labels="labelsCost"
						length="3"
						empty-icon="mdi-circle-outline"
						full-icon="mdi-circle"
						hover
					>
						<template v-slot:item-label="props">
							<span>
								{{ props.label }}
							</span>
						</template>
					</v-rating>
				</div> -->

				<h3>Choix des ingrédients</h3>
				<Ingredients
				v-model:ingredients="form.ingredients" />

				<!-- <h3>Commentaires</h3>
				<v-textarea
					label="Ecrit ton commentaire"
					variant="underlined"
				></v-textarea> -->

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
import Ingredients from "@/components/Ingredients.vue";

export default {
	name: "createRecipe",
	components: {
		Ingredients,
	},
	data() {
		return {
			form: {
				recipeName: "",
				recipePicture: null,
				ingredients:[],
			},
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
	mounted(){
console.log("validation: ", this.v$);
	},

	computed: {
		recipeNameErrors() {
			if (!this.v$.form.recipeName.$error) {
				return [];
			}
			const errors = [];
			const rules = this.v$.form.recipeName;
			if (rules.required.$invalid) errors.push(messages.required);
			if (rules.minLength.$invalid) errors.push(messages.minLength(4));
			if (rules.maxLength.$invalid) errors.push(messages.maxLength(100));
			return errors;
		},
		recipePictureErrors() {
			const errors = [];
			const rules = this.v$.form.recipePicture;

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
			this.submitted = true;

			// On valide le formulaire ici
			await this.v$.$validate();

			// Si le formulaire est invalide, on arrête la soumission
			if (this.v$.$invalid) {
				console.log("Formulaire invalide", this.v$.$errors);
				return;
			}
			try {
				const recipesStore = useRecipesStore();
				const response = await recipesStore.addRecipe(this.form);

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

.recipeForm {
	max-width: 800px;
	margin: auto;
}

.v-form {
	background-color: white;
	width: 100%;
}

.v-text-field,
.v-file-input {
	max-width: auto;
	margin-bottom: 30px;
	padding-left: 20px;
	color: #5d827f;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}
</style>
