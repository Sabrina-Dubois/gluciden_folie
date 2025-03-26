<template>
	<v-app-bar class="custom-app-bar">
		<!-- Menu principal -->
		<v-menu
			v-if="!isCreateRecipe && !isCreateCategory && !isLoginPage"
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
		<v-app-bar-title class="title">Glucid'en Folie</v-app-bar-title>

		<!-- Barre de recherche -->
		<v-text-field
			v-if="!isCreateRecipe && !isCreateCategory && !isLoginPage"
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
			v-if="!isCreateRecipe && !isCreateCategory && !isLoginPage"
			@click="goToConnection"
			class="connexion"
			ml-5
			rounded=""
			prepend-icon="mdi-account"
		>
			{{ isAuthenticated ? "Se déconnecter" : "Se connecter" }}
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
			// 1. Récupère le token JWT depuis le localStorage
			const token = localStorage.getItem("jwt");
			// 2. Si pas de token, l'utilisateur n'est pas admin
			if (!token) return false;
			try {
				// 3. Décodage du token JWT (qui a 3 parties séparées par des points)
				// atob() décode une string en base64
				// On prend la partie payload (index 1) qui contient les infos utilisateur
				const payload = JSON.parse(atob(token.split(".")[1]));
				// Solution 1 (si vous utilisez la claim "role")
				return payload.role === "ROLE_ADMIN";
				// 4. Vérifie si le tableau 'roles' contient 'ROLE_ADMIN'
				// Le ?. est l'opérateur de chaînage optionnel (évite les erreurs si roles est undefined)
				return payload.roles?.includes("ROLE_ADMIN");
			} catch {
				return false;
			}
		},
		isAuthenticated() {
			return !!localStorage.getItem("jwt"); // Vérifie la présence du token
		},
		isCreateRecipe() {
			return this.$route.name === "createRecipe";
		},
		isCreateCategory() {
			return this.$route.name === "createCategory";
		},
		// Vérification si on est sur la page de login ou register
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


