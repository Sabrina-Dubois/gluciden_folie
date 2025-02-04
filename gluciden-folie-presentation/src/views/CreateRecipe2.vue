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
					:error-messages="getRecipeNameErrorMessages()"
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
					:error-messages="getRecipePictureErrorMessages()"
					class="required-field"
					chips
					prepend-icon="mdi-camera"
					variant="underlined"
				></v-file-input>

				<!-- Nombre de portions -->
				<h3 class="section-title portions-title">
					Nombre de portion{{ $t("create_recipe.portions") }}
				</h3>
				<v-text-field
					v-model="numberOfPortions"
					type="number"
					label="Nombre de portions"
					hide-details
					variant="underlined"
				></v-text-field>

				<h3>Temps de préparation</h3>
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
				</v-container>

				<h3>Temps de cuisson</h3>
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
				</v-container>

				<h3 class="text-h6 mb-2">Type de cuisson</h3>
				<v-container class="mx-auto" max-width="400">
					<v-chip-group v-model="amenities" column multiple>
						<v-chip text="Sans cuisson" variant="outlined" filter></v-chip>
						<v-chip text="Micro-onde" variant="outlined" filter></v-chip>
						<v-chip text="Four" variant="outlined" filter></v-chip>
						<v-chip text="Poêle" variant="outlined" filter></v-chip>
						<v-chip text="Vapeur" variant="outlined" filter></v-chip>
					</v-chip-group>
				</v-container>

				<v-container class="boo pa-0" fluid>
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
				</v-container>

				<h3>Difficultés</h3>
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
				</div>

				<h3>Coûts</h3>
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
				</div>

				<h3>Choix des ingrédients</h3>
				<v-container class="ingredients-list">
					<v-row>
						<v-col
							v-for="(ingredient, index) in ingredients"
							:key="index"
							cols="1"
							sm="1"
							md="1"
							lg="1"
							hide-details
							@click="selectIngredient(index)"
						>
							<v-img
								:src="ingredient.image"
								contain
								class="ingredient-img"
							></v-img>
							<!-- <v-card-subtitle class="text-center">
									{{ ingredient.name }}
								</v-card-subtitle> -->
						</v-col>
					</v-row>
				</v-container>
				<h3>Commentaires</h3>
				<v-textarea
					label="Ecrit ton commentaire"
					variant="underlined"
				></v-textarea>

				<v-btn class="custom-btn" ml-5 rounded="" type="submit">
					{{ $t("create_recipe.button") }}
				</v-btn>
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
	name: "createRecipe",
	data() {
		return {
			recipeName: "",
			recipePicture: null,
			imagePreview: null,
			cooks: [
				{ name: "Grill", icon: "mdi-fire" },
				{ name: "Baking", icon: "mdi-oven" },
				{ name: "Boil", icon: "mdi-water" },
			],
			ingredients: [
				{
					image:
						"https://www.agir-crt.com/wp-content/uploads/2014/06/chocolat.jpg",
				},
				{
					name: "Milk",
					image:
						"https://upload.wikimedia.org/wikipedia/commons/0/0e/Milk_glass.jpg",
				},
				{
					name: "Egg",
					image:
						"https://i0.wp.com/technomitron.aainb.com/wp-content/uploads/2018/09/word-image-93.jpeg?resize=622%2C426&ssl=1",
				},
				{
					name: "Sugar",
					image: "https://www.plantes-et-sante.fr/images/sucre.jpg",
				},
				{
					name: "Apple",
					image: "https://www.norabio.fr/wp-content/uploads/2024/03/Pomme.png",
				},
				{
					name: "Banana",
					image:
						"https://mapetiteassiette.com/wp-content/uploads/2019/05/shutterstock_553887610-e1557046359887.jpg",
				},
			],
			v$: null,
			submitted: false,
		};
	},
	validations() {
		return recipeValidation;
	},
	created() {
		this.v$ = useVuelidate(); // Initialisation de Vuelidate },
	},
	methods: {
		handleFileUpload(event) {
			const file = event.target.files[0];
			if (file) {
				this.recipePicture = file;
				this.imagePreview = URL.createObjectURL(file);
			}
		},
		async cipe() {
			this.submitted = true; // formulaire soumis
			this.v$.$touch(); // Marque tous les champs comme touchés
			if (this.v$.$invalid) {
				console.log("Formulaire invalide");
				return; // Si le formulaire est invalide, arrête la soumission }
			}
			const formData = new FormData();
			formData.append("name", this.recipeName);
			formData.append("picture", this.recipePicture);

			try {
				const response = await apiClient.post("/recipes", formData, {
					headers: {
						"Content-Type": "multipart/form-data",
					},
				});

				if (response.status === 200) {
					this.recipeName = "";
					this.recipePicture = null;
					this.submitted = false;

					console.log("Recette créée avec succès !");
					this.$router.push({ name: "recipesList" });
				} else {
					console.error("Erreur lors de la création de la recette");
				}
			} catch (error) {
				console.error(error);
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
