<template>
	<v-container class="home-comp-container" fluid>
		<h1>{{ $t("home.title") }}</h1>

		<v-card class="d-flex mb-4" elevation="4">
			<h3>{{ $t("home.recipeOfTheDay.title") }}</h3>
			<v-container fluid>
				<v-row>
					<!-- Description -->
					<v-col cols="6" class="d-flex align-center justify-center ">
						<p class="description">
							{{ $t("home.recipeOfTheDay.description") }}
						</p>
					</v-col>

					<!-- Photo -->
					<v-col cols="6" class="d-flex justify-start">
						<v-card class="recipe-card" width="430px" flat>
							<v-img
								:src="cards[0].src"
								class="align-start"
								height="200px"
								cover
								rounded
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
			<h3>{{ $t("home.foodieFavorite.title") }}</h3>
			<v-card class="carroussel" elevation="4">
				<v-carousel hide-delimiters>
					<v-carousel-item
						v-for="(item, i) in items"
						:key="i"
						:src="item.src"
						cover
					>
						<div class="text">{{ item.title }}</div>
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
				{ src: "/images/Pancakes.jpg", title: "Pancakes" },
				{ src: "/images/Tarte aux pommes.jpg", title: "Tarte aux pommes" },
				{ src: "/images/Pudding chia coco.jpg", title: "Pudding chia coco" },
				{ src: "/images/Banana bread.jpeg", title: "Banana bread" }
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
/* *** Container *** */
.home-comp-container {
	max-width: 900px;
	margin: auto;
	padding: 0 16px;
}
.mb-4 {
	margin-bottom: 16px;
}

/* *** Recette du jour *** */
.description {
	color: #5d827f;
	font-size: 20px;
	text-align: center;
	display: flex;
	height: 100%;
}

/* *** Titre *** */
.card-title {
	color: white;
	font-size: 25px;
	text-align: top;

}
.v-app-bar-title {
	flex: 1;
	text-align: start;
	font-size: 40px;
	font-weight: bold !important;
	color: #5d827f;
}

/* *** Bouton *** */
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

/* *** Carrousel *** */
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