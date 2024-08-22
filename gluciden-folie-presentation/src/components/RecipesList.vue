<template>
	<v-container class="Recip-List-container" fluid>
		<v-card class="d-flex mx-auto my-auto">
			<h1>LISTE DES RECETTES</h1>
			<v-container fluid>
				<v-row dense>
					{{ recipes }}
					<!-- Card image & boutons -->
					<v-col
						v-for="recipe in recipes"
						:key="recipe.name"
						cols="12"
						md="3"
						class="d-flex justify-center ml-4"
					>
						<v-card class="recip-card d-flex flex-column align-center" flat>
							<v-img
								:src="'/images/' + recipe.picture"
								class="recipe-picture"
								height="200px"
								cover
								rounded=""
							>
								<v-card-title class="text-white">
									{{ recipe.name }}
								</v-card-title>
							</v-img>
							<v-card-actions class="button d-flex">
								<v-btn icon="mdi-heart"> </v-btn>
								<v-btn icon="mdi-share-variant"></v-btn>
								<v-btn
									icon="mdi-pencil"
									@click="updateRecipe(recipe.id)"
								></v-btn>
								<v-btn 
									icon="mdi-delete" 
									@click="deleteRecipe(recipe.id)">
								</v-btn>
							</v-card-actions>
						</v-card>
					</v-col>
				</v-row>
			</v-container>
		</v-card>
	</v-container>
</template>

<script scoped>
export default {
	name: "recipesList",
	data() {
		//fonction qui retourne un objet contenant les données réactives du composant->retourne un array items qui contient les src des images à afficher dans le carrousel.
		return {
			recipes: [],
		};
	},
	//regarder
	beforeMount() {
		this.initRecipes();
	},

	methods: {
		async initRecipes() {
			// pour les fichiers via HTTP -> gestion de l'encodage
			// Json est ok pour les données textuelles mais pas pour transmettre des données binaires
			// objet qui prends les options du fetch par defaut fetch est un GET
			try {
				const response = await fetch("http://localhost:8080/recipes");
				const json = await response.json();
				this.recipes = json;
			} catch (error) {
				console.error(error);
			}
		},
		async deleteRecipe(recipeId) {
			if (confirm("Veux-tu supprimer cette recette ?")) {
				try {
					const response = await fetch(
						`http://localhost:8080/recipes/${recipeId}`,
						{
							method: "DELETE",
						}
					);
					if (response.ok) {
						// Supprimer la recette de la liste affichée
						//this.recipes = this.recipes.filter(
						//(recipe) => recipe.id !== recipeId
						//);

						//avantage -> ne pas mettre de logique métier dans le front
						//inconvéniant -> 1 appel htpp en +
						await this.initRecipes();
						alert("Recette supprimée");
					} else {
						alert("Erreur lors de la suppression de la recette");
					}
				} catch (error) {
					console.error("Erreur:", error);
				}
			}
		},
		//renvoyer vers la page update
		updateRecipe(recipeId) {
			this.$router.push({ name: "updateRecipe", params: { id: recipeId } });
		},
	},
};
</script>

<style scoped>
.Recip-List-container {
	max-width: 900px;
}

.ml-4 {
	margin-left: 50px;
}
/* Day recipe */

.v-card-actions {
	height: 10px;
}

.button {
	justify-content: center;
}
.v-btn {
	color: #5d827f;
	margin: 0px 00px;
	background-color: #f5ede8;
}

.v-btn:hover,
.v-btn--active {
	background-color: #5d827f;
	color: #f5ede8 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.fav-btn {
	color: #f29eb0;
}

.fav-btn:hover,
.fav-btn--active {
	background-color: #5d827f;
	color: #f29eb0 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.v-app-bar-title {
	flex: 1;
	text-align: start;
	font-size: 40px;
	font-weight: bold !important;
	color: #5d827f;
}
</style>