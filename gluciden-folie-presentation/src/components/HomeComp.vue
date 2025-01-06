<template>
	<v-container class="home-comp-container" fluid>
		<h1>{{ $t("home.title") }}</h1>
		
		<v-card class="d-flex mb-4">
			<h3>{{ $t("home.recipeOfTheDay.title") }}</h3>
			<v-container fluid>
				<v-row>
					<!-- Description -->
					<v-col cols-6 class="d-flex align-center">
						<p class="description">
							{{ $t("home.recipeOfTheDay.description") }}
						</p>
					</v-col>

					<!-- Card image & boutons -->
					<v-col v-for="card in cards" :key="card.title" :cols="card.flex">
						<v-card class="recip-card d-flex justify-end" width="480px" flat>
							<v-img
								:src="cards[0].src"
								class="align-end"
								height="200px"
								width="430px"
								cover
								rounded=""
							>
								<v-card-title class="card-title">
									{{ cards[0].title }}
								</v-card-title>
							</v-img>

							<v-card-actions class="d-flex">
								<v-spacer></v-spacer>
								<v-btn icon="mdi-heart"></v-btn>
								<v-btn icon="mdi-share-variant"></v-btn>
								<v-spacer></v-spacer>
							</v-card-actions>
						</v-card>
					</v-col>
				</v-row>
			</v-container>
		</v-card>

		<!-- Carroussel -->
		<v-card>
		<h3> {{ $t("home.foodieFavorite.title") }}</h3>
		<v-card class="carroussel">
			<v-carousel hide-delimiters>
				<v-carousel-item
					v-for="(item, i) in items"
					:key="i"
					:src="item.src"
					cover
				>
					<div class="text">Mousse au chocolat</div>
				</v-carousel-item>
			</v-carousel>
		</v-card>
		</v-card>
	</v-container>
</template>

<script scoped>
export default {
	name: "homeComp",
	data() {
		//fonction qui retourne un objet contenant les données réactives du composant->retourne un array items qui contient les src des images à afficher dans le carrousel.
		return {
			items: [
				//array d'objets:chaque objet ayant une propriété src qui pointe vers une URL d'image->images utilisées par le carrousel pour afficher chaque élément.
				{ src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg" },
				{ src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg" },
				{ src: "https://cdn.vuetifyjs.com/images/carousel/bird.jpg" },
				{ src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg" },
			],
			cards: [
				{
					title: "Carrot cake",
					src: "https://flipdish.imgix.net/y1Xqez7kfY0ueFmsx4BA7agj0Y.jpg",
					flex: 6,
				},
			],
		};
	},
};
</script>

<style scoped>
.home-comp-container {
	max-width: 900px;
	margin: auto;
}

.mb-4 {
	margin-bottom: 16px;
}
/* Day recipe */

.description {
	color: #5d827f;
	font-size: 20px;
	text-align: center;
	margin-top: 70px;
}

.card-title {
	color: #5d827f;
	font-size: 25px;
}

.v-btn {
	color: #5d827f;
	size: "large";
	margin: 0px 20px;
	background-color: #f5ede8;
}

.v-btn:hover,
.v-btn--active {
	background-color: #5d827f;
	color: #f5ede8 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.v-app-bar-title {
	flex: 1;
	text-align: start;
	font-size: 40px;
	font-weight: bold !important;
	color: #5d827f;
}

/* Carrousel */

.text {
	position: absolute;
	width: 100%;
	text-align: center;
	color: white;
	font-size: 25px;
	padding: 10px;
}
.carroussel {
	overflow: visible;
	max-width: 900px;
	margin: auto;
}
</style>