<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("create_recipe.page.title") }}</h1>
		<v-card class="recipeForm d-flex align-center">
			<v-form @submit.prevent="newRecipe">
				<h3>{{ $t("create_recipe.page.recipe_title.recipe_title") }}</h3>
				<v-text-field
					v-model="recipeName"
					:label="$t(`create_recipe.page.recipe_title.label`)"
					hide-details
					variant="underlined"
				></v-text-field>

				<!-- <h3>Type de desserts & Régime alimentaire</h3>
				<v-container class="pa-0" fluid>
					<v-row class="d-flex">
						<v-col
							v-for="(type, index) in types"
							:key="index"
							cols="12"
							md="3"
							no-gutters
							@click="selectItem(index)"
						>
							<v-card>
								<v-icon v-if="type.icon">
									{{ type.icon }}
								</v-icon>

								<v-card-subtitle class="text-center">
									{{ type.name }}
								</v-card-subtitle>
							</v-card>
						</v-col>
					</v-row>
				</v-container> -->

				<!-- <h3>Nombre de portion</h3>
				<h3>Temps de préparation</h3>

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

				<h3>Type de cuisson</h3>
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
				</div> -->

				<h3>{{ $t("create_recipe.page.picture.picture") }}</h3>
				<v-img
					v-if="imagePreview"
					:src="imagePreview"
					class="recipe-picture"
					height="200px"
					cover
					rounded=""
				></v-img>

				<v-file-input
					v-model="recipePicture"
					accept="image/png, image/jpeg"
					:label="$t(`create_recipe.page.picture.label`)"
					chips
					prepend-icon="mdi-camera"
					variant="underlined"
					@change="handleFileUpload"
				>
				</v-file-input>

				<!-- <h3>Choix des ingrédients</h3>

				<h3>Commentaires</h3>
				<v-textarea
					label="Ecrit ton commentaire"
					variant="underlined"
				></v-textarea> -->

				<v-btn class="custom-btn" ml-5 rounded="" type="submit">{{
					$t("create_recipe.page.button")
				}}</v-btn>
			</v-form>
		</v-card>
	</div>
</template>

<script>
import apiClient from "../api/axiosConfig";

export default {
	name: "createRecipe",
	//data -> retourne obj qui contient les données réctives du composant
	data() {
		return {
			recipeName: "", // modèle nom initialise et utiliser le v-model dans le template
			recipePicture: null,
			imagePreview: null,
		};
	},
	methods: {
		handleFileUpload(event) {
			const file = event.target.files[0]; // Récupérez le fichier sélectionné
			if (file) {
				this.recipePicture = file; // Mettez à jour la propriété de l'image
				this.imagePreview = URL.createObjectURL(file); // Créez une URL de prévisualisation
			}
		},
		async newRecipe() {
			//pour les fichiers via HTTP -> gestion de l'encodage
			//Json est ok pour les données textuelles mais pas pour transmettre des données binaires
			const formData = new FormData();
			formData.append("name", this.recipeName);
			formData.append("picture", this.recipePicture); // image du formulaire

			try {
				const response = await apiClient.post("/recipes", formData)

				if (response.status === 200) {
					//pour réinitialiser les variables lorsque le validation est OK
					this.recipeName = "";
					this.recipePicture = null;

					console.log("Recette crée avec succès !");
					//Renvoi liste de recettes
					this.$router.push({ name: "recipesList" });
				} else {
					console.error("Erreur lors de la création de la recette");
				}
			} catch (error) {
				console.error(error);
			}
		},
		selectedItem(index) {
			this.selectedItem = index;
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

h1 {
	text-align: center;
	margin: 20px;
}

h3 {
	color: #5d827f;
}

.v-icon,
.v-rating {
	color: #f29eb0;
}
.recipeForm {
	max-width: 900px;
	margin: auto;
}

.v-card {
	background-color: white;
	text-align: center;
	margin: 0 auto;
	padding: 20px;
}
/* . {
	max-width: 120px;
} */
.v-form {
	background-color: white;
}

.v-text-field,
.v-file-input {
	max-height: 20px;
	max-width: auto;
	margin-bottom: 90px;
	color: #5d827f;
}

.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}

/* Wrapper :envelopper les items de v-rating -> justify-content: space-between*/
.v-rating__wrapper span {
	margin: 0 10px;
}
</style>