<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("update_category.title") }}</h1>
		<v-card class="categoryForm d-flex align-center" elevation="4">
			<!-- Formulaire pour modifier une catégorie -->
			<v-form @submit.prevent="updateCategory">

				<v-text-field
					v-model="categoryName"
					:label="$t(`update_category.category.label`) + ' *'"
					:error="v$.categoryName.$error"
					:error-messages="getCategoryNameErrorMessages()"
					hide-details="auto"
					variant="underlined"
				></v-text-field>
				<v-btn
					class="custom-btn"
					ml-5
					rounded=""
					@click="updateCategory"
					type="submit"
					>{{ $t("update_category.button") }}</v-btn
				>
			</v-form>
		</v-card>
	</div>
</template>

<script>
import { useCategoriesStore } from "@/stores/categoriesStore.js";
import apiClient from "../api/axiosConfig";
import { categoryValidation } from "../utils/validationRules.js";
import useVuelidate from "@vuelidate/core";
import i18n from "@/i18n/i18n"; 

export default {
	name: "updateCategory",
	data() {
		return {
			id: this.$route.params.id, // Récupération de l'ID de la catégorie
			categoryName: "",
			v$: null,
			submitted: false,
		};
	},
	created() {
    this.v$ = useVuelidate(); // Initialisation de Vuelidate
    this.initCategory(); // Récupère la catégorie à modifier
  },
	validations() {
		return categoryValidation;
	},
	methods: {
		async initCategory() {
			try {
				const response = await apiClient.get(`/categories/${this.id}`);
				this.categoryName = response.data.name;
			} catch (error) {
				console.error("Erreur lors de la récupération de la catégorie:", error);
			}
		},

		// Mise à jour de la catégorie
		async updateCategory() {
			this.submitted = true;
			this.v$.$touch();
			if (this.v$.$invalid) return;
			try {
				await useCategoriesStore().updateCategory(this.id, this.categoryName);
				this.$router.push({ name: "categoriesList" });
			} catch (error) {
				console.error("Erreur lors de la mise à jour de la catégorie:", error);
			}
		},
		getCategoryNameErrorMessages() {
			const errors = [];
			console.log("Validation status:", this.v$.categoryName);

			if (this.v$.categoryName.$error) {
				if (this.v$.categoryName.required.$invalid) {
					errors.push(i18n.global.t("validation.required"));
				}
				if (this.v$.categoryName.minLength.$invalid) {
					errors.push(i18n.global.t("validation.minLength", { min: 4 }));
				}
				if (this.v$.categoryName.maxLength.$invalid) {
					errors.push(i18n.global.t("validation.maxLength", { max: 50 }));
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

.categoryForm {
	max-width: 900px;
	margin: auto;
}

.v-text-field {
	margin-bottom: 20px;
	color: #5d827f;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}

</style>