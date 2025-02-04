<template>
	<div class="auth-form">
		<h1>
			{{ isLogin ? $t("auth.toggleToLogin") : $t("auth.toggleToRegister") }}
		</h1>
		<v-card class="connection d-flex align-center">
			<v-form @submit.prevent="newAuth" :model="v$">
				<!-- Email -->
				<div class="field-container">
					<label class="custom-label"
						>{{ $t("auth.email.label-mail") }} *</label
					>
					<v-text-field
						v-model="username"
						class="text-field"
						:placeholder="$t('auth.email.placeholder-mail')"
						density="compact"
						variant="solo"
						prepend-inner-icon="mdi-email"
						:error="v$.username.$error"
						:error-messages="usernameErrors"
						hide-details="auto"
					></v-text-field>
				</div>
				<!-- Password -->
				<div class="field-container">
					<label class="custom-label"
						>{{ $t("auth.password.label-pass") }} *</label
					>

					<v-text-field
						v-model="password"
						class="text-field"
						:type="showPassword ? 'text' : 'password'"
						:placeholder="$t('auth.password.placeholder-pass')"
						variant="solo"
						prepend-inner-icon="mdi-lock"
						:append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
						@click:append-inner="togglePasswordVisibility"
						:error="v$.password.$error"
						:error-messages="passwordErrors"
						density="compact"
					></v-text-field>
				</div>

				<!-- Bouton d'action -->
				<v-btn class="custom-btn" ml-5 rounded type="submit">
					{{ isLogin ? $t("auth.button-connexion") : $t("auth.button-auth") }}
				</v-btn>
			</v-form>
			<!-- Switch entre connexion et inscription -->
			<p>
				{{ isLogin ? $t("auth.toggleToRegister") : $t("auth.toggleToLogin") }}
				<v-btn rounded @click="toggleMode">
					{{ isLogin ? $t("auth.button-auth") : $t("auth.button-connexion") }}
				</v-btn>
			</p>
		</v-card>
	</div>
</template>

<script>
import apiClient from "../api/axiosConfig";
import useVuelidate from "@vuelidate/core";
import { accountValidation } from "../utils/validationRules";
import { messages } from "../utils/validationMessages.js";

export default {
	name: "authentification",
	data() {
		return {
			isLogin: true,
			username: "",
			password: "",
			showPassword: false,
			submitted: false,
			v$: null,
		};
	},
	validations() {
		return accountValidation;
	},
	created() {
		this.v$ = useVuelidate();
		const token = localStorage.getItem("jwt");
		if (token && !this.isLogin) {
			// Si le token est présent et qu'on est sur la page d'inscription, rediriger vers la page de login
			this.$router.push({
				name: "authentification",
				params: { action: "home" },
			});
		}
	},

	computed: {
		usernameErrors() {
			const errors = [];
			const rules = this.v$.username;

			if (rules.$error) {
				if (rules.required.$invalid) errors.push(messages.required);
				if (this.v$.username.validEmail.$invalid)
					errors.push(messages.validEmail);
				if (rules.minLength.$invalid) errors.push(messages.minLength(8));
				if (rules.maxLength.$invalid) errors.push(messages.maxLength(50));
			}

			return errors;
		},

		passwordErrors() {
			const errors = [];
			const rules = this.v$.password;

			if (rules.$error) {
				if (rules.required.$invalid) errors.push(messages.required);
				if (rules.validPassword.$invalid) errors.push(messages.validPassword);
				if (rules.minLength.$invalid) errors.push(messages.minLength(8));
				if (rules.maxLength.$invalid) errors.push(messages.maxLength(72));
			}

			return errors;
		},
	},

	methods: {
		toggleMode() {
			this.isLogin = !this.isLogin;
			const action = this.isLogin ? "login" : "register";
			this.$router.push({ name: "authentification", params: { action } });
		},
		togglePasswordVisibility() {
			this.showPassword = !this.showPassword;
		},
		logout() {
			localStorage.removeItem("jwt"); // Supprime le token du localStorage
			this.$router.push({ name: "home" }); // Redirection vers la page de connexion
		},
		async newAuth() {
			this.submitted = true;
			this.v$.$touch();

			if (this.v$.$invalid) {
				console.log("Formulaire invalide");
				return;
			}

			const userData = {
				username: this.username,
				password: this.password,
			};
			console.log("Données envoyées au backend :", userData);

			try {
				if (this.isLogin) {
					const response = await apiClient.post("accounts/login", userData);

					if (response.status === 200) {
						console.log("Utilisateur connecté avec succès !");
						localStorage.setItem("jwt", response.data.token); // Sauvegarde du token
						this.$router.push({ name: "home" }); // Redirection vers la page d'accueil après connexion
						this.isAuthenticated = true; 
					} else {
						console.warn("Erreur lors de la connexion :", response);
						alert(
							"Impossible de se connecter. Veuillez vérifier vos informations."
						);
					}
				} else {
					const response = await apiClient.post("/accounts", userData);
					if (response.status === 201) {
						console.log("Compte créé avec succès !");
						this.$router.push({
							name: "authentification",
							params: { action: "login" },
						});
					} else {
						console.warn("Erreur lors de la création du compte :", response);
						alert("Impossible de créer un compte. Veuillez réessayer.");
					}
				}
			} catch (error) {
				console.error(error);
			}
		},
	},
};
</script>

<style scoped>
.auth-form {
	max-width: 500px;
	margin: auto;
	padding: 20px;
	text-align: center;
}

.connection {
	background-color: #f3f3f3;
	padding: 20px;
	border-radius: 10px;
}

.field-container {
	margin-bottom: 15px;
	text-align: left;
}

.custom-label {
	font-size: 16px;
	margin-bottom: 5px;
	color: #333;
}

.v-btn {
	margin-top: 10px;
	background-color: #5d827f;
	color: #d3beb1;
}

.v-btn:hover {
	background-color: #4a6b68;
}

p {
	margin-top: 15px;
	color: #5d827f;
}

.connection {
	max-width: 800px;
	margin: auto;
	padding: 20px;
}

h2 {
	font-family: "Laila", serif;
	font-weight: 400;
	font-style: normal;
}

.v-card {
	background-color: #d3beb1;
}

.text-field {
	/* Réduction de la largeur des champs */ /* Largeur minimale pour des écrans plus petits */
	max-width: 800px;
	align-content: center;
	margin: 10px; /* Espacement entre les champs */
}

.field-container {
	display: flex;
	flex-direction: column;
}
.custom-label {
	font-size: 16px;
	color: #5d827f;
	margin-left: 20px;
	padding-bottom: 0px;
	text-align: left; /* Le label est aligné à gauche */
	width: 100%; /* Prendre toute la largeur disponible */
}
</style>