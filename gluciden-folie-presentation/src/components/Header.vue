<template>
	<v-app-bar class="custom-app-bar">
		<!-- Menu principal -->
		<v-menu
			v-if="!isAuthentification && !isCreateRecipe && !isCreateCategory"
			v-model="menuOpen"
			offset-y
			class="text-center"
		>
			<template v-slot:activator="{ props }">
				<v-btn icon v-bind="props">
					<v-icon>mdi-menu</v-icon>
				</v-btn>
			</template>

			<v-list>
				<!-- Accueil -->
				<v-list-item @click="goToHome">
					<v-list-item-title>Accueil</v-list-item-title>
				</v-list-item>

				<!-- Favoris -->
				<v-list-item>
					<v-list-item-title>Favoris</v-list-item-title>
				</v-list-item>

				<!-- Création de recettes -->
				<v-list-item @click="isCreateCategory">
					{{ $t("header.link_create") }}
				</v-list-item>

				<!-- Liste des recettes -->
				<v-list-item @click="recipesList">
					{{ $t("header.link_list_recipes") }}
				</v-list-item>

				<!-- Création des catégories -->
				<v-list-item @click="isCreateCategory"> Catégories </v-list-item>

				<!-- Liste des catégories -->
				<v-list-item @click="categoriesList">
					{{ $t("header.link_list_categories") }}
				</v-list-item>

				<!-- Test -->
				<v-list-item @click="test">
					{{ $t("header.link_test") }}
				</v-list-item>

				<!-- Langue -->
				<v-menu
					:open-on-focus="false"
					v-model="languageMenuOpen"
					offset-y
					variant
					submenu
				>
					<template v-slot:activator="{ props }">
						<v-list-item
							v-bind="props"
							class="d-flex justify-space-between"
							variant="text"
						>
							<v-list-item-title class="langue">Langues</v-list-item-title>
							<template v-slot:append>
								<v-icon icon="mdi-menu-right" size="x-small"></v-icon>
							</template>
						</v-list-item>
					</template>

					<!-- Sous-parties : Français et Anglais -->
					<v-list-item @click="setLanguage('fr')">
						<span class="flag-icon">🇫🇷</span>
					</v-list-item>
					<v-list-item @click="setLanguage('en')">
						<span class="flag-icon">🇺🇸</span>
					</v-list-item>
				</v-menu>
			</v-list>
		</v-menu>

		<!-- Logo -->
		<router-link to="/" exact>
			<img alt="Vue logo" class="logo" src="@/assets/images/logo.png" />
		</router-link>

		<!-- Titre -->
		<v-app-bar-title class="title">Glucid'en Folie</v-app-bar-title>

		<!-- Barre de recherche -->
		<v-text-field
			v-if="!isAuthentification && !isCreateRecipe && !isCreateCategory"
			class="custom-text-field"
			append-inner-icon="mdi-magnify"
			density="compact"
			label="Je cherche : un ingrédient, une recette..."
			variant="solo"
			hide-details
			single-line
		></v-text-field>

		<v-spacer></v-spacer>

		<!-- Connexion ou déconnexion -->
		<v-btn
			v-if="!isAuthentification && !isCreateRecipe && !isCreateCategory"
			@click="goToConnection"
			class="connexion"
			ml-5
			rounded=""
			prepend-icon="mdi-account"
		>
			{{ buttonText }}
		</v-btn>
	</v-app-bar>
</template>

<script>
export default {
	name: "Header",
	data() {
		return {
			menuOpen: false,
			languageMenuOpen: false,
		};
	},
	computed: {
		isAuthentification() {
			return this.$route.name === "authentification";
		},
		// Verif page Création recettes et cat
		isCreateRecipe() {
			return this.$route.name === "createRecipe";
		},
		isRecipesList() {
			return this.$route.name === "recipesList";
		},
		isCreateCategory() {
			return this.$route.name === "createCategory";
		},
		isCatgoriesList() {
			return this.$route.name === "categoriesList";
		},
		buttonText() {
			return this.isAuthenticated ? "Se déconnecter" : "Se connecter";
		},
		isAuthenticated() {
			return !!localStorage.getItem("jwt");
		},
		isTested() {
			return this.$route.name === "test";
		},
	},
	methods: {
		goToConnection() {
			if (this.isAuthenticated) {
				this.logout(); // Déconnexion
			} else {
				this.$router.push({
					name: "authentification",
					params: { action: "login" },
				});
			}
		},
		goToHome() {
			this.$router.push({ name: "home" }); // Utilise $router.push pour naviguer vers la page d'accueil
		},
		setLanguage(lang) {
			this.$i18n.locale = lang;
			alert(`Langue changée en ${lang === "fr" ? "Français" : "Anglais"}`);
		},
		logout() {
			// Supprime le token d'authentification
			localStorage.removeItem("jwt");
			// Redirige vers la page d'accueil et recharge la page
			this.$router.push({ name: "home" }).then(() => {
				window.location.reload(); // Recharge la page pour mettre à jour l'état d'authentification
			});
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
	justify-content: space-between;
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
.connexion {
	position: absolute;
	right: 30px;
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
/* *** Liste *** */
.v-list-item {
	background-color: white;
	color: #5d827f;
}
.v-list-item:hover {
	background-color: #f5ede8 !important;
	color: #d3beb1 !important; /* Assurer que la couleur du texte au survol reste héritée */
}

.flag-icon {
	justify-content: center;
	padding-left: 10px;
	font-size: 32px;
}
</style>


