<template>
	<div class="main-content custom-bg">
		{{ newCategory }}
		<h1>{{ $t("create_category.page.title") }}</h1>
		<v-card class="recipeForm d-flex align-center">
			<v-form @submit.prevent="createCategory">
				<h3>{{ $t("create_category.page.category_title.category_title") }}</h3>
				<v-text-field
					v-model="newCategory"
					:label="$t(`create_category.page.category_title.label`)"
					hide-details
					variant="underlined"
				></v-text-field>
				<div class="button-container">
					<v-btn class="custom-btn" ml-5 rounded="" type="submit">{{
						$t("create_category.page.button")
					}}</v-btn>
					
				</div>
			</v-form>
		</v-card>
	</div>
</template>

<script>
import apiClient from "../api/axiosConfig";

export default {
	name: "createCategory",
	data() {
		return {
			newCategory: "",
		};
	},
	methods: {
		async createCategory() {
			if (this.newCategory.trim() !== "") {
				try {
					await apiClient.post("/categories", { name: this.newCategory });
					this.$emit("categoryCreated", this.newCategory);
					this.newCategory = "";

					//Renvoi onglets catégories
					this.$router.push({ name: "home" });
				} catch (error) {
					console.error(" erreur lors de la création de la atégorie:", error);
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
/* . {
	max-width: 120px;
} */
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
/* *** Boutons *** */
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