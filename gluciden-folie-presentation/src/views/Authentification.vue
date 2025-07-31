<template>
	<v-container class="auth-form" fluid>
		<v-row justify="center">
			<v-col cols="12" sm="8" md="6" lg="4">
				<h1>
					{{ isLogin ? $t("auth.toggleToLogin") : $t("auth.toggleToRegister") }}
				</h1>
				<v-card class="connection" elevation="4">
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
						<v-btn class="custom-btn" type="submit" rounded block>
							{{
								isLogin ? $t("auth.button-connexion") : $t("auth.button-auth")
							}}
						</v-btn>
					</v-form>

					<!-- Switch entre connexion et inscription -->
					<p>
						{{
							isLogin ? $t("auth.toggleToRegister") : $t("auth.toggleToLogin")
						}}
						<v-btn rounded @click="toggleMode">
							{{
								isLogin ? $t("auth.button-auth") : $t("auth.button-connexion")
							}}
						</v-btn>
					</p>
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import apiClient from "../api/axiosConfig";
import useVuelidate from "@vuelidate/core";
import { accountValidation } from "../utils/validationRules";
import i18n from "@/i18n/i18n.js"; 
//import { messages } from "../utils/validationMessages.js";

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

		const action = this.$route.params.action;

		if (action === "login") {
			this.isLogin = true;
		} else if (action === "register") {
			this.isLogin = false;
		}

		if (token && !this.isLogin) {
			// Si le token est présent et qu'on est sur la page d'inscription, rediriger vers la page de login
			this.$router.push({
				name: "authentification",
				params: { action: "login" },
			});
		}
	},
	computed: {
		usernameErrors() {
			const errors = [];
			const rules = this.v$.username;
			if (rules.$error) {
				if (rules.required.$invalid) errors.push(i18n.global.t("validation.required"));
				if (this.v$.username.validEmail.$invalid)
					errors.push(i18n.global.t("validation.validEmail"));
				if (rules.minLength.$invalid) errors.push(i18n.global.t("validation.minLength", { min: 8 }));
				if (rules.maxLength.$invalid) errors.push(i18n.global.t("validation.maxLength", { max: 50 }));
			}
			return errors;
		},
		passwordErrors() {
			const errors = [];
			const rules = this.v$.password;
			if (rules.$error) {
				if (rules.required.$invalid) errors.push(i18n.global.t("validation.required"));
				if (rules.validPassword.$invalid) errors.push(i18n.global.t("validation.validPassword"));
				if (rules.minLength.$invalid) errors.push(i18n.global.t("validation.minLength", { min: 8 }));
				if (rules.maxLength.$invalid) errors.push(i18n.global.t("validation.maxLength", { max: 72 }));
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

			const userData = { username: this.username, password: this.password };
			try {
				if (this.isLogin) {
					const response = await apiClient.post("accounts/login", { username : this.username, password : this.password });
					if (response.status === 200) {
						console.log("Utilisateur connecté avec succès !");
						localStorage.setItem("jwt", response.data.token);
						this.$router.push({ name: "home" });
					} else {
						alert("Erreur de connexion");
					}
				} else {
					const response = await apiClient.post("/accounts", userData);
					if (response.status === 201) {
						this.$router.push({
							name: "authentification",
							params: { action: "login" },
						});
					} else {
						alert("Erreur lors de la création du compte");
					}
				}
			} catch (error) {
				console.error(
					"Erreur lors de la connexion : ",
					error.response ? error.response.data : error.message
				);
				if (error.response && error.response.status === 401) {
					alert(
						"Identifiants incorrects. Veuillez vérifier votre email et votre mot de passe."
					);
				} else {
					alert("Une erreur s'est produite. Veuillez réessayer plus tard.");
				}
			}
		},
	},
};
</script>

<style scoped>
.auth-form {
	padding: 20px;
}

.connection {
	background-color: #d3beb1;
	padding: 20px;
	
	border-radius: 10px;
}

.text-field {
	width: 100%;
	margin: 10px 0;
}

.field-container {
	display: flex;
	flex-direction: column;
}

.custom-label {
	font-size: 16px;
	color: #5d827f;
	margin-left: 20px;
	text-align: left;
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
	color: #5d827f;
}
</style>
