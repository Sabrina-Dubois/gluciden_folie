<template>
	<v-app-bar class="custom-app-bar">
		<!-- Menu principal -->
		<v-menu
			v-if="
				!isCreateRecipe &&
				!isCreateCategory &&
				!isLoginPage &&
				!isUpdateRecipe &&
				!isUpdateCategory
			"
			v-model="menuOpen"
			offset-y
			class="text-center"
		>
			<template v-slot:activator="{ props }">
				<v-btn icon="mdi-menu" v-bind="props"
					aria-label="$t('header.menu_button')"></v-btn>
			</template>

			<v-list>
				<!-- Accueil -->
				<v-list-item @click="goToHome">
					<v-list-item-title>Accueil</v-list-item-title>
				</v-list-item>

				<!-- Création de recettes -->
				<v-list-item v-if="isAdmin" @click="goToCreateRecipe">
					{{ $t("header.create_recipe") }}
				</v-list-item>

				<!-- Liste des recettes -->
				<v-list-item @click="recipesList">
					{{ $t("header.list_recipes") }}
				</v-list-item>

				<!-- Création de catégories -->
				<v-list-item v-if="isAdmin" @click="goToCreateCategory">
					{{ $t("header.create-category") }}
				</v-list-item>

				<!-- Liste des catégories -->
				<v-list-item v-if="isAdmin" @click="categoriesList">
					{{ $t("header.list_categories") }}
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
		<v-app-bar-title class="title hidden-sm-and-down"
			>Glucid'en Folie</v-app-bar-title
		>

		<!-- Barre de recherche -->
		<v-text-field
			v-if="
				!isCreateRecipe &&
				!isCreateCategory &&
				!isLoginPage &&
				!isUpdateRecipe &&
				!isUpdateCategory &&
				!isCategoriesList
			"
			class="custom-text-field hidden-sm-and-down"
			append-inner-icon="mdi-magnify"
			density="compact"
			label="Je cherche : un ingrédient, une recette..."
			variant="solo"
			hide-details
			single-line
		></v-text-field>

		<!-- Loupe visible seulement en mobile -->
		<v-btn
			v-if="
				!isCreateRecipe &&
				!isCreateCategory &&
				!isLoginPage &&
				!isUpdateRecipe &&
				!isUpdateCategory
			"
			icon
			class="hidden-md-and-up"
			aria-label="Rechercher"
		>
			<v-icon>mdi-magnify</v-icon>
		</v-btn>

		<v-spacer></v-spacer>

		<!-- Connexion ou déconnexion -->
		<v-btn
			v-if="!isCreateRecipe && !isCreateCategory && !isLoginPage"
			@click="goToConnection"
			class="connexion hidden-sm-and-down"
			ml-5
			rounded=""
			prepend-icon="mdi-account"
			:aria-label="isAuthenticated ? 'Se déconnecter' : 'Se connecter'"
		>
			{{ isAuthenticated ? "Se déconnecter" : "Se connecter" }}
		</v-btn>
		<v-btn
			v-if="!isCreateRecipe && !isCreateCategory && !isLoginPage"
			@click="goToConnection"
			icon
			class="hidden-md-and-up"
			:aria-label="isAuthenticated ? 'Se déconnecter' : 'Se connecter'"
		>
			<v-icon>mdi-account</v-icon>
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
		isAdmin() {
			const token = localStorage.getItem("jwt");
			if (!token) return false;
			try {
				const payload = JSON.parse(atob(token.split(".")[1]));
				return (
					payload.role === "ROLE_ADMIN" || payload.roles?.includes("ROLE_ADMIN")
				);
			} catch {
				return false;
			}
		},
		isAuthenticated() {
			return !!localStorage.getItem("jwt");
		},
		isCreateRecipe() {
			return this.$route.name === "createRecipe";
		},
		isCreateCategory() {
			return this.$route.name === "createCategory";
		},
		isCategoriesList() {
			return this.$route.name === "categoriesList";
		},
		isUpdateRecipe() {
			return this.$route.name === "updateRecipe";
		},
		isUpdateCategory() {
			return this.$route.name === "updateCategory";
		},
		isLoginPage() {
			return (
				this.$route.params.action === "login" ||
				this.$route.params.action === "register"
			);
		},
	},
	methods: {
		goToConnection() {
			if (this.isAuthenticated) {
				this.logout();
			} else {
				this.$router.push({
					name: "authentification",
					params: { action: "login" },
				});
			}
		},
		goToHome() {
			this.$router.push({ name: "home" });
		},
		goToCreateRecipe() {
			this.$router.push({ name: "createRecipe" });
		},
		goToCreateCategory() {
			this.$router.push({ name: "createCategory" });
		},
		setLanguage(lang) {
			this.$i18n.locale = lang;
			alert(`Langue changée en ${lang === "fr" ? "Français" : "Anglais"}`);
		},
		recipesList() {
			this.$router.push({ name: "recipesList" });
		},
		categoriesList() {
			this.$router.push({ name: "categoriesList" });
		},
		logout() {
			localStorage.removeItem("jwt");
			this.$router.push({ name: "home" });
		},
	},
};
</script>

<style scoped>
/* *** Titre *** */
.title {
	overflow: visible !important;
}
.v-app-bar-title {
	flex: 1;
	text-align: start;
	font-size: 40px;
	font-weight: bold !important;
	color: #5d827f;
}

/* *** Barre application *** */
.custom-app-bar {
	background-color: #d3beb1 !important;
	display: flex;
	align-items: center;
	padding: 0 20px;
	min-height: 100px;
	justify-content: space-between;
}

/* *** Logo *** */
.logo {
	width: 115px;
	height: auto;
}

/* *** Bouton *** */
.v-btn {
	margin: 5px 0; /* Espacement entre les boutons */
	background-color: #f5ede8;
	color: #5d827f;
}
.connexion {
	position: absolute;
	right: 30px;
}
.v-btn:hover,
.v-btn--active {
	background-color: #5d827f;
	color: #f5ede8 !important;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

/* *** Liste *** */
.v-list {
	justify-items: start;
}
.v-list-item {
	background-color: white;
	color: #5d827f;
}
.v-list-item:hover {
	background-color: #f5ede8 !important;
	color: #d3beb1 !important;
}
.flag-icon {
	justify-content: center;
	padding-left: 10px;
	font-size: 32px;
}
</style>