<template>
	<CategoriesLayout :showCreateCategory="false" />
	<HomeComp />
</template>

<script>
import apiClient from "@/api/axiosConfig";
import HomeComp from "../components/HomeComp.vue";
import CategoriesLayout from "../components/CategoriesLayout.vue";

export default {
	name: "Home",
	components: {
		HomeComp,
		CategoriesLayout
	},
	data() {
		//initialiser des données mais pas de manipulation des données
		return {
			categories: [],
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
		async fetchCategories() {
			try {
				const response = await apiClient.get("/categories");
				this.items = response.data
			} catch(error) {
				console.error("Erreur lors de la récupératio des catégories:", error)
			}
		},
	},
	mounted() {
		// Charger les catégories à l'initialisation du composant
		this.fetchCategories();
		//const layout = new CategoriesLayout();
		//this.categories = layout.categories;
	},
};
</script>

<style>
</style>