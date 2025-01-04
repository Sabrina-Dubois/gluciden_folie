<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("create_category.title") }}</h1>

		<!-- Création de la catégorie-->
		<v-card class="categoryForm d-flex align-center">
			<v-form @submit.prevent="createCategory">
				<h3>{{ $t("create_category.category.name") }}</h3>
				<!-- Création de la catégorie-->
				<v-text-field
					v-model="categoryName"
					:label="$t(`create_category.category.label`) + ' *'"
					:error="v$.categoryName.$error"
					:error-messages="getCategoryNameErrorMessages()"
					hide-details="auto"
					variant="underlined"
				></v-text-field>
				<v-btn class="custom-btn" ml-5 rounded="" type="submit">
					{{ $t("create_category.button") }}
				</v-btn>
			</v-form>
		</v-card>
	</div>
</template>

<script>
import { useCategoriesStore } from "@/stores/categoriesStore.js";
import { categoryValidation } from "../utils/validationRules.js";
import useVuelidate from "@vuelidate/core";
import { messages } from "../utils/validationMessages.js";

export default {
	name: "createCategory",
	data() {
		return {
			categoryName: "",
			v$: null,
			submitted: false,
		};
	},
	validations() {
		return categoryValidation;
	},
	created() {
		this.v$ = useVuelidate(); // Initialisation de Vuelidate ,
	},
	methods: {
		// submitForm() {
		// 	const categoryStore = useCategoriesStore();
		// 	categoryStore.addCategory(this.categoryName);
		// 	this.categoryName = ""; // Réinitialiser le champ de saisie
		// },
		// async fetchCategories() {
		// 	try {
		// 		const response = await apiClient.get("/categories");
		// 		//console.log(response.data);
		// 		this.categories = response.data;
		// 	} catch (error) {
		// 		console.error("Erreur lors du chargement des catégories :", error);
		// 	}
		// },
		async createCategory() {
			this.submitted = true; // formulaire soumis
			this.v$.$touch(); // Marque tous les champs comme touchés
			if (this.v$.$invalid) {
				console.log("Formulaire invalide");
				return; // Si le formulaire est invalide, arrête la soumission }
			}
			if (this.categoryName.trim() !== "") {
				try {
					const categoryStore = useCategoriesStore();
					await categoryStore.addCategory(this.categoryName);
					this.$emit("categoryCreated", this.categoryName);
					this.categoryName = "";
					this.submitted = false;
					this.$router.push({ name: "home" });
				} catch (error) {
					console.error("Erreur lors de la création de la catégorie :", error);
				}
			}
		},
		getCategoryNameErrorMessages() {
			const errors = [];
			console.log("Validation status:", this.v$.categoryName);

			if (this.v$.categoryName.$error) {
				if (this.v$.categoryName.required.$invalid) {
					errors.push(messages.required);
				}
				if (this.v$.categoryName.minLength.$invalid) {
					errors.push(messages.minLength(4));
				}
				if (this.v$.categoryName.maxLength.$invalid) {
					errors.push(messages.maxLength(50));
				}
			}
			return errors;
		},
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

.v-icon,
.v-rating {
	color: #f29eb0;
}
.categoryForm {
	max-width: 900px;
	margin: auto;
}

.v-form {
	background-color: white;
}

.v-text-field,
.v-file-input {
	max-height: 20px;
	max-width: auto;
	padding-left: 20px;
	margin-bottom: 90px;
	color: #5d827f;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}

/* *** Validation *** */
.v-text-field--error .v-input__control,
.v-file-input--error .v-input__control {
	border-color: #ff5252;
}
.v-text-field--error .v-input__label,
.v-file-input--error .v-input__label {
	color: #ff5252;
}
.v-text-field--error .v-input__icon,
.v-file-input--error .v-input__icon {
	color: #ff5252;
}
.v-messages__message {
	color: red !important;
	font-weight: bold;
}

/* Wrapper pour espacer les éléments de v-rating */
.v-rating__wrapper span {
	margin: 0 10px;
}
</style>
