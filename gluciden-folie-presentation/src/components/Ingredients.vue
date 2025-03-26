<template>
	<v-container>
		<!-- Liste des ingrédients ajoutés en Chips -->
		<div>
			<v-chip-group multiple>
				<v-chip
					v-for="(ingredient, index) in ingredients"
					:key="index"
					class="ma-1"
					closable
					@click:close="deleteIngredient(index)"
				>
					{{ ingredient.name }} : {{ ingredient.quantity }}
					{{ ingredient.unit }}
				</v-chip>
				<v-chip closable @click:close="deleteIngredient(index)"> hello </v-chip>
			</v-chip-group>
		</div>

		<!-- Formulaire d'ajout d'ingrédient -->
		<v-row>
			<!-- Ingrédient -->
			<v-col cols="12" md="6">
				<v-combobox
					v-model="newIngredient.name"
					:items="ingredientList"
					label="Ingrédients"
					variant="underlined"
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
					v-model="newIngredient.unit"
					:items="unitList"
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
export default {
	data() {
		return {
			ingredients: [],
			newIngredient: {
				name: "",
				quantity: "",
				unit: "",
			},
			ingredientList: [
				"Farine",
				"Maizena",
				"Sucre",
				"Lait",
				"Chocolat noir",
				"Oeuf",
			],
			unitList: ["g", "kg", "ml", "L", "c. à soupe", "c. à café", "unité"], // Options pour l'unité
		};
	},
	computed: {
		canAdd() {
			// Vérifie si les champs sont tous remplis avant d'ajouter un ingrédient
			return (
				this.newIngredient.name &&
				this.newIngredient.quantity &&
				this.newIngredient.unit
			);
		},
	},
	methods: {
		addIngredient() {
			// Ajoute l'ingrédient à la liste si tous les champs sont valides
			if (this.canAdd) {
				this.ingredients.push({ ...this.newIngredient });
				// Réinitialise le formulaire après ajout
				this.newIngredient = {
					name: "",
					quantity: "",
					unit: "",
				};
			}
		},
		deleteIngredient(index) {
			// Supprime un ingrédient de la liste
			this.ingredients.splice(index, 1);
		},
	},
};
</script>

<style scoped>
.add-icon {
	color: #5d827f !important;
}
/* Changer la couleur du texte de tous les composants en vert */
.v-chip,
.v-text-field input {
	color: #5d827f
}


.v-text-field{

	color: #5d827f;
}
</style>
