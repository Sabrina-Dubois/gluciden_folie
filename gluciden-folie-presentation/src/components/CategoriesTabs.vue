<template>
	<v-card>
		<!-- Création des onglets -->
		<v-tabs 
            v-model="tab"
            class="tab"
            :value="tab">
			<!-- Contenu des onglets -->
			<v-tab
				v-for="item in categories"
				:key="item"
				:value="item"
				class="tab"
				:elevation="8"
				@click="$emit('updateActiveItemC', item)"
			>

				<!-- Affiche le nom de l'élément dans l'onglet -->
				{{ item.name }}
			</v-tab>
		</v-tabs>
	</v-card>
</template>

<script>
export default {
	name: "CategoriesTabs",
	data() {},
	props: {
		categories: {
			type: Array,
			default: [],
		},
	},
	// récuperer évenement
	methods: {
		temp(item) {
			console.log(item);
		},
	},
};
</script>

<style scoped>
.v-card {
	width: 1000px;
	margin: auto;
    background-color: #5d827f !important;
    display: flex;
    justify-content:center;
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