<template>
	<v-container class="Recip-List-container" fluid>
		<v-card class="d-flex mx-auto my-auto">
			<h1>LISTE DES {{ category.name }}</h1>
			<v-container fluid>
				<v-row dense>
					<!-- Card image & boutons -->
					<v-col
						v-for="recipe in category.recipes"
						:key="recipe.name"
						cols="12"
						md="3"
						class="d-flex justify-center ml-4"
					>
						<v-card class="recip-card d-flex flex-column align-center" flat>
							<v-img
								src="https://flipdish.imgix.net/y1Xqez7kfY0ueFmsx4BA7agj0Y.jpg"
								class="recipe-image"
								height="200px"
								cover
								rounded=""
							>
								<v-card-title class="text-white">
									{{ recipe.name }}
								</v-card-title>
							</v-img>
							<v-card-actions class="d-flex">
								<v-spacer></v-spacer>
								<v-btn
									:class="{ 'fav-btn': recipe.fav === true }"
									icon="mdi-heart"
									@click="recipe.fav = !recipe.fav"
								>
								</v-btn>
								<v-btn icon="mdi-share-variant"></v-btn>
								<v-spacer></v-spacer>
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
	name: "recipesCategories",
	data() {
		//fonction qui retourne un objet contenant les données réactives du composant->retourne un array items qui contient les src des images à afficher dans le carrousel.
		return {};
	},
	props: {
		category: {
			type: Object, //homeView items
			default: {},
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
.v-btn {
	color: #5d827f;
	size: "small" !important;
	margin: 0px 20px;
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