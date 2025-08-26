<template>
	<v-card>
		<!-- Création des onglets -->
		<v-tabs v-model="tab" class="tab" :value="tab">
			<!-- Contenu des onglets -->
			<v-tab
				v-for="category in categories"
				:key="category.id"
				:value="category.name"
				class="tab"
				
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
	data() {
		return {
			tab: null,
		};
	},
	computed: {
		categories() {
			const categoriesStore = useCategoriesStore();
			return categoriesStore.categories;
		},
	},
	watch: {
		categories(newCategories) {
			if (newCategories.length > 0 && !this.tab) {
				this.tab = newCategories[0].name;
			}
		},
	},
	created() {
		const categoriesStore = useCategoriesStore();
		this.$watch(
			() => categoriesStore.categories,
			(newVal) => {
				this.tab = newVal.length > 0 ? newVal[0].name : null;
			}
		);
	},
};
</script>

<style scoped>
.v-card {
	width:auto;
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

.tab--active {
	background-color: #d3beb1;
	color: #5d827f !important;
}
</style>