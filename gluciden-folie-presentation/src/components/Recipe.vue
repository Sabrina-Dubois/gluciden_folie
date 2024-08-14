<template>
	<v-container class="home-comp-container">
		<v-card class="d-flex mb-4">
			<h1>CARROT CAKE</h1>
			<v-container fluid>
				<v-row>
					<v-col v-for="card in cards" :key="card.title" :cols="card.flex">
						<v-card class="recip-card d-flex justify-end" flat>
							<v-img
								:src="card.src"
								class="align-center"
								height="200px"
								width="480px"
								cover
								rounded=""
							>
							</v-img>

							<v-card-actions class="d-flex">
								<v-spacer></v-spacer>
								<v-btn icon="mdi-heart"></v-btn>
								<v-btn icon="mdi-bookmark"></v-btn>
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
	name: "recipe",
	data() {
		//fonction qui retourne un objet contenant les données réactives du composant->retourne un array items qui contient les src des images à afficher dans le carrousel.
		return {
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

.h1 {
	color: #5d827f;
	font-size: 25px;
}

.v-card-actions {
	height: 10px;
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
</style>