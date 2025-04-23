<template>
	<v-container>
		<!-- Liste des ingrédients ajoutés en Chips -->
		<div>
			<v-chip-group :key="ingredients.length">
				<v-chip
					v-for="(ingredient, index) in ingredients"
					:key="index"
					class="ma-1"
					closable
					@click:close="deleteIngredient(index)"
				>
					{{ ingredient.ingredientName }} : {{ ingredient.quantity }}
					{{ unitiesUnique.find((el) => el.id === ingredient.unity).name }}
				</v-chip>
				<!-- <v-chip closable @click:close="deleteIngredient(index)"> hello </v-chip> -->
			</v-chip-group>
		</div>

		<!-- Formulaire d'ajout d'ingrédient -->
		<v-row>
			<!-- Ingrédient -->
			<v-col cols="12" md="6">
				<v-combobox
					v-model="newIngredient.ingredientName"
					:items="ingredientsList"
					label="Ingrédients"
					variant="underlined"
					hide-details
				></v-combobox>
			</v-col>

			<!-- Quantité -->
			<v-col cols="6" md="3">
				<v-text-field
					v-model="newIngredient.quantity"
					label="Quantité"
					variant="underlined"
					type="number"
				></v-text-field>
			</v-col>

			<!-- Unité -->
			<v-col cols="6" md="2">
				<v-combobox
					v-model="newIngredient.unity"
					:items="unitiesUnique"
					item-title="name"
					item-value="id"
					:return-object="false"
					label="Unité"
					variant="underlined"
				></v-combobox>
			</v-col>

			<!-- Icône Ajouter (Alignée à droite) -->
			<v-col cols="12" md="1" class="d-flex align-center justify-end">
				<v-icon
					color="primary"
					class="add-icon"
					@click="addIngredient"
					:class="{ 'icon-disabled': !canAdd }"
				>
					mdi-plus-circle
				</v-icon>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import { useRecipesStore } from "@/stores/recipesStore.js";
import { useIngredientsStore } from "@/stores/ingredientsStore.js";

export default {
	name: "ingredients",
	props: {
		ingredients: {
			type: Array,
			default: () => [],
		},
	},
	data() {
		return {
			ingredientsStore: useIngredientsStore(),
			recipesStore: useRecipesStore(),
			newIngredient: {
				ingredientName: "",
				quantity: "",
				unity: "",
			},
			// ingredients: [],
			//unities: [], // Options pour l'unité
		};
	},
	computed: {
		//acces au store
		ingredientsList() {
			return this.recipesStore.ingredientList;
		},
		unitiesUnique() {
			// return this.ingredientsStore.unitiesList.map((unity) => unity.name);
			return this.ingredientsStore.unitiesList;
		},
		canAdd() {
			// Vérifie si les champs sont tous remplis avant d'ajouter un ingrédient
			return (
				this.newIngredient.ingredientName &&
				this.newIngredient.quantity &&
				this.newIngredient.unity
			);
		},
		canCreate() {
			return this.ingredients.length > 0;
		},
	},
	created() {
		this.ingredientsStore.fetchUnities(); // ✅ Charge les unités dès que le composant est monté
	},
	methods: {
		addIngredient() {
			// Ajoute l'ingrédient à la liste si tous les champs sont valides
			if (this.canAdd) {
				//this.ingredients.push({ ...this.newIngredient });
				// this.ingredientsStore.addIngredient({
				// name: this.newIngredient.name,
				// quantity: this.newIngredient.quantity,
				// unity: this.newIngredient.unity,
				// }); // Réinitialise le formulaire après ajout
				let updatedIng = [...this.ingredients, this.newIngredient];
				this.$emit("update:ingredients", updatedIng);
				this.newIngredient = { ingredientName: "", quantity: "", unity: "" };
			}
		},
		deleteIngredient(index) {
			// Supprime un ingrédient de la liste
			// this.ingredients.splice(index, 1);
			let updatedIng = [...this.ingredients];
			updatedIng.splice(index, 1);
			this.$emit("update:ingredients", updatedIng);
		},
	},
};
</script>

<style scoped>
.add-icon {
	color: #5d827f !important;
}
.v-chip,
.v-text-field input {
	color: #5d827f;
}

.v-text-field {
	color: #5d827f;
}
</style>
