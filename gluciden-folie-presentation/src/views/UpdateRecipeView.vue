<template>
	<div class="main-content custom-bg">
		<h1>Modifier une nouvelle recette</h1>
		<v-card class="recipeForm d-flex align-center">
			<v-form @submit.prevent="submitForm">
				<!--subitForm ->method valider données saisies / gérer mgs erreurs
				//v-model -> form la valeur saisie màj auto //v-model créer un lien
				bidirectionnel entre l'interface utilisateur et les données du composant-->

				<h3>Titre de la recette</h3>
				<v-text-field
					v-model="recipeName"
					label="Nom de la recette"
					hide-details
					variant="underlined"
				></v-text-field>

				<h3>Photo</h3>
				<!-- <v-file-input
					v-model="recipePicture"
					accept="image/png, image/jpeg"
					label="Télécharger la photo de la recette w/ chips"
					chips
					variant="underlined"
				>
				</v-file-input> -->
				<v-file-input label="File input"
				v-model="recipePicture"></v-file-input>

				<v-btn class="custom-btn" ml-5 rounded="" type="submit"
					>Enregistrer</v-btn
				>
			</v-form>
		</v-card>
	</div>
</template>

<script>
export default {
	name: "updateRecipe",
	//data -> retourne obj qui contient les données réctives du composant
	data() {
		return {
			id: this.$route.params.id, //recup le param id de la route -> stocker dans la data id
			recipeName: "", // modèle nom initialise et utiliser le v-model dans le template
			recipePicture: null,
		};
	},
	beforeMount() {
		this.initRecipe();
	},
	methods: {
		submitForm() {},
		async initRecipe() {
			// pour les fichiers via HTTP -> gestion de l'encodage
			// Json ests ok pour les données textuelles mais pas pour transmettre des données binaires
			// objet qui prends les options du fetch par defaut fetch est un GET
			try {
				const response = await fetch(
					`http://localhost:8080/recipes/${this.id}`
				);
				const json = await response.json();
				this.id = json;
				this.recipeName = json.name;
				this.recipePicture = json.picture;
			} catch (error) {
				console.error(error);
			}
		},
		// updateRecipe() {
		// 	//pour les fichiers via HTTP -> gestion de l'encodage
		// 	//Json ests ok pour les données textuelles mais pas pour transmettre des données binaires
		// 	const formData = new FormData();
		// 	formData.append("name", this.recipeName);
		// 	formData.append("picture", this.recipePicture); // image du formulaire

		// 	//objet qui prends les options du fetch par defaut fetch est un GET
		// 	const options = {
		// 		method: "GET",
		// 		body: formData,
		// 	};
		// 	try {
		// 		fetch("http://localhost:8080/recipes", options);
		// 	} catch (error) {
		// 		console.error(error);
		// 	}
		// },
	},
};
</script>

<style scoped>
.main-content.custom-bg {
	padding-top: 10px;
}

.d-flex {
	align-items: center;
	justify-content: center;
}

h1 {
	text-align: center;
	margin: 20px;
}

h3 {
	color: #5d827f;
}

.v-icon,
.v-rating {
	color: #f29eb0;
}
.recipeForm {
	max-width: 900px;
	margin: auto;
}

.v-card {
	background-color: white;
	text-align: center;
	margin: 0 auto;
	padding: 20px;
}
.timeCook {
	max-width: 120px;
}
.v-form {
	background-color: white;
}

.v-text-field,
.v-file-input {
	max-height: 20px;
	max-width: auto;
	margin-bottom: 90px;
	color: #5d827f;
}

.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}

/* Wrapper :envelopper les items de v-rating -> justify-content: space-between*/
.v-rating__wrapper span {
	margin: 0 10px;
}
</style>