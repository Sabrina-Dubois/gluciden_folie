<template>
	<v-app-bar class="custom-app-bar">
		<v-app-bar-nav-icon></v-app-bar-nav-icon>
		<router-link to="/" exact>
			<img alt="Vue logo" class="logo" src="@/assets/images/logo.png" />
		</router-link>
		<!-- Conteneur pour les boutons en colonne -->
		<v-col class="d-flex flex-column align-center">
			<v-btn class="mb-2">
				<router-link to="/test">{{ $t("header.link_test") }}</router-link>
			</v-btn>
			<v-btn class="mb-2">
				<router-link :to="{ name: 'recipesList' }">{{
					$t("header.link_list_recipes")
				}}</router-link>
			</v-btn>
			<v-btn class="mb-2">
				<router-link :to="{ name: 'createRecipe' }">{{
					$t("header.link_create")
				}}</router-link>
			</v-btn>
			<v-btn class="mb-2">
				<router-link :to="{ name: 'createCategory' }">Catégorie</router-link>
			</v-btn>
			<v-btn class="mb-2">
				<router-link :to="{ name: 'categoriesList' }">{{ $t("header.link_list_categories") }}</router-link>
			</v-btn>
			
		</v-col>

		<v-app-bar-title class="title">Glucid'en Folie</v-app-bar-title>
		<v-btn icon="mdi-heart"></v-btn>
		<v-text-field
			class="custom-text-field"
			append-inner-icon="mdi-magnify"
			density="compact"
			label="Je cherche : un ingrédient, une recette..."
			variant="underlined"
			hide-details
			single-line
		></v-text-field>
		<v-btn
			@click="goToConnection"
			class="custom-btn"
			ml-5
			rounded=""
			prepend-icon="mdi-account"
		>
			{{ $t("header.button") }}
		</v-btn>
	</v-app-bar>
</template>

<script>
export default {
	name: "Header",
	methods: {
		goToConnection() {
			this.$router.push("/connection");
		},
	},
};
</script>

<style scoped>
.title {
	overflow: visible !important;
}
.custom-app-bar {
	background-color: #d3beb1 !important;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 0 20px;
	min-height: 100px;
}

.custom-text-field {
	width: auto;
	background-color: white;
	color: #5d827f;
}

.logo {
	width: 115px;
	height: auto;
}

.v-btn {
	margin: 5px 0; /* Espacement entre les boutons */
	background-color: #f5ede8;
	color: #5d827f;
}

.v-app-bar-title {
	flex: 1;
	text-align: start;
	font-size: 40px;
	font-weight: bold !important;
	color: #5d827f;
}

.v-btn:hover,
.v-btn--active {
	background-color: #5d827f;
	color: #f5ede8 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}
</style>
