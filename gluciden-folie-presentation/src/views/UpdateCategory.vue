<template>
	<div class="main-content custom-bg">
		<h1>{{ $t("update_category.page.title") }}</h1>
		<v-card class="categoryForm d-flex align-center">
			<v-form @submit.prevent="updateCategory">
				<h3>{{ $t("update_category.page.category_title.category_title") }}</h3>
				<v-text-field
					v-model="categoryName"
					label="Nom de la catégorie"
					hide-details
					variant="underlined"
				></v-text-field>
				<v-btn
					class="custom-btn"
					ml-5
					rounded=""
					@click="updateCategory"
					type="submit"
					>{{ $t("update_category.page.button") }}</v-btn
				>
			</v-form>
		</v-card>
	</div>
</template>

<script>
import apiClient from "../api/axiosConfig";

export default {
	name: "updateCategory",
	data() {
		return {
			id: this.$route.params.id, // Récupération de l'ID de la catégorie
			categoryName: "",
		};
	},
	beforeMount() {
		this.initCategory();
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
			const formData = new FormData();
			formData.append("name", this.categoryName);

			try {
				const response = await apiClient.put(
					`/categories/${this.id}`,
					formData
				);

				if (response.status === 200) {
					console.log("Catégorie mise à jour avec succès !");
					this.$router.push({ name: "categoriesList" });
				} else {
					console.error("Erreur lors de la mise à jour de la catégorie");
				}
			} catch (error) {
				if (error.response) {
					console.error(
						"Erreur réseau lors de la mise à jour:",
						error.response.data
					);
				} else {
					console.error("Erreur réseau lors de la mise à jour:", error.message);
				}
			}
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

h1 {
	text-align: center;
	margin: 20px;
}

h3 {
	color: #5d827f;
}

.categoryForm {
	max-width: 900px;
	margin: auto;
}

.v-card {
	background-color: white;
	text-align: center;
	margin: 0 auto;
	padding: 20px;
}

.v-text-field {
	margin-bottom: 20px;
	color: #5d827f;
}

.v-btn {
	background-color: #5d827f;
	color: #d3beb1;
}
</style>