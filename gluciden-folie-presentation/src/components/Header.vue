<template>
	<v-app-bar class="custom-app-bar">
		<v-app-bar-nav-icon></v-app-bar-nav-icon>
		<img alt="Vue logo" class="logo" src="@/assets/images/logo.png" />
		<router-link to="/test">TEST</router-link>
		<br/>
		<router-link to="/createRecipe">Ajouter une recette</router-link>
		<router-link to="/updateRecipe">Modifier une recette</router-link>
		<router-link to="/recipesList">Liste des recettes</router-link>
		<v-app-bar-title class="title">Glucid'en Folie</v-app-bar-title>
		<v-btn icon="mdi-heart"></v-btn>
		<v-text-field
			class="custom-text-field"
			append-inner-icon="mdi-magnify"
			density="compact"
			label="Je cherche : un ingrèdient, une recette..."
			variant="underlined"
			hide-details
			single-line
		></v-text-field>
		<v-btn @click="goToConnection" class="custom-btn" ml-5 rounded="" prepend-icon="mdi-account"
		>
			Connexion
		</v-btn>
		<spacer></spacer>
	</v-app-bar>
</template>

<script>
export default {
	name: "Header",
	//attribut
	methods: {
		goToConnection(){
			this.$router.push('/connection')
		}
	}
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
	margin: 0px 20px;
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
