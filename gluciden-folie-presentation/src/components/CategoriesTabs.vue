<template>
	<v-card>
		<!-- Création des onglets -->
		<v-tabs v-model="tab" class="tab" :value="tab">
			<!-- Contenu des onglets -->
			<v-tab
				v-for="category in categories"
				:key="category.name"
				:value="category.name"
				class="tab"
				:elevation="8"
			>
				<!-- Affiche le nom de l'élément dans l'onglet -->
				{{ category.name }}
			</v-tab>
		</v-tabs>
	</v-card>
</template>

<script>
import { useCategoriesStore } from "@/stores/categoriesStore.js";

export default {
	name: "CategoriesTabs",
	// props: {
	// 	categories: {
	// 		type: Array,
	// 		required: true,
	// 	},
	// },
	data() {
		return {
			tab: null,
		};
	},

	computed: {
		categories() {
			return useCategoriesStore().categories;
		},
	},
	// récuperer évenement
	// methods: {
	// 	addCategory(newCategory) {
	// 		this.categories.push(newCategory);
	// 		this.tab = newCategory.name; // Définit la nouvelle catégorie comme onglet actif
	// 	},
	// },
	created() {
		//ecouter event 'categoryCreated' composant enfant
		//this.$emit("categoryCreated", this.addCategory);
		useCategoriesStore().fetchCategories();
	},
	// Ajouter la nouvelle catégorie à la liste des catégories après création réussie
	createCategory() {
		console.log("Nom de la catégorie:", this.newCategory);
		const formData = new FormData();
		formData.append("name", this.newCategory);
		const options = { method: "POST", body: formData };

		try {
			fetch("http://localhost:8080/categories", options)
				.then((response) => {
					if (response.ok) {
						console.log("Catégorie créée avec succès !");
						this.$emit("categoryCreated", this.newCategory);
						this.newCategory = ""; // Réinitialiser après succès
					} else {
						console.error("Erreur lors de la création de la catégorie");
					}
				})
				.catch((error) => {
					console.error("Erreur lors de la requête API :", error);
				});
		} catch (error) {
			console.error("Erreur inattendue :", error);
		}
	},
};
</script>

<style scoped>
.v-card {
	width: 1000px;
	max-height: 70px;
	margin: auto;
	background-color: #5d827f !important;
	display: flex;
	justify-content: center;
}

.tab {
	background-color: #5d827f;
	color: #d3beb1;
	font-style: bold;
}
.tab:hover,
.tab--active {
	background-color: #d3beb1;
	color: #5d827f !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}
</style>