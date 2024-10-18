<template>
	<CategoriesTabs :categories="items" @updateActiveItemC="updateActiveItemP" />
	<!-- si le nombre de clé de l'objet > 0  prendre toutes les clé pour en faire un array-->
	<RecipesList
		:category="activeItem"
		v-if="Object.keys(activeItem).length !== 0"
	/>
	<HomeComp v-else />
</template>

<script>
import HomeComp from "../components/HomeComp.vue";
import CategoriesTabs from "@/components/CategoriesTabs.vue";
import RecipesList from "@/components/RecipesList.vue";

export default {
	name: "Home",
	components: {
		CategoriesTabs,
		HomeComp,
		RecipesList,
	},
	data() {
		//initialiser des données mais pas de manipulation des données
		return {
			items: [
				{
					name: "Sans Lactose",
					recipes: [
						{
							name: "Carrot cake",
							image: "carrotcake.png",
							fav: true,
						},
						{
							name: "Gateau au yaourt",
							image: "yaourt.png",
							fav: false,
						},
						{
							name: "Gâteau au chocolat",
							image: "chocolat.png",
							fav: false,
						},
					],
				},
				{
					name: "boissons",
					recipes: [
						{
							name: "Jus de pamplemousse",
							image: "pamplemousse.png",
							fav: true,
						},
						{
							name: "Chocolat chaud maison",
							image: "chococho.png",
							fav: true,
						},
						{
							name: "Smoothie de banane et coco",
							image: "bananacoco.png",
							fav: false,
						},
					],
				},
				{
					name: "minis",
					recipes: [
						{
							name: "Muffin",
							image: "muffin.png",
							fav: true,
						},
						{
							name: "Cookie",
							image: "cookie.png",
							fav: true,
						},
						{
							name: "Madeleine",
							image: "madeleine.png",
							fav: false,
						},
					],
				},
				{
					name: "crêpes",
					recipes: [
						{
							name: "Crêpe nutella",
							image: "nutella.png",
							fav: true,
						},
						{
							name: "Crêpe allégée",
							image: "crepe.png",
							fav: false,
						},
						{
							name: "Crêpe au sucre",
							image: "sucre.png",
							fav: false,
						},
					],
				},
				{
					name: "verrines",
					recipes: [
						{
							name: "Verrine carotte aubergine",
							image: "carotte.png",
							fav: false,
						},
						{
							name: "Verrine d'hiver",
							image: "hiver.png",
							fav: true,
						},
						{
							name: "Verrine de printemps",
							image: "spring.png",
							fav: false,
						},
					],
				},
			],
			activeItem: {},
		};
	},
	//mettre a jour la valeur activeItemP POUR PRENDRE la valeur de item (arg)
	methods: {
		updateActiveItemP(item) {
			this.activeItem = item;
		},
	},
};
</script>

<style>
</style>