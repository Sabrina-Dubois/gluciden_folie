<template>
	<v-container>
		<!-- Chips des ingrédients -->
		<div v-if="unitiesUnique.length">
			<v-chip-group :key="ingredients.length">
				<v-chip
					v-for="(ingredient, index) in ingredients"
					:key="index"
					class="ma-1"
					closable
					@click:close="deleteIngredient(index)"
				>
					{{ ingredient.ingredient.name }} : {{ ingredient.quantity }}
					{{ getUnityName(ingredient.unityId) }}
				</v-chip>
			</v-chip-group>
		</div>

		<!-- Formulaire d'ajout d'ingrédient -->
		<v-row>
			<!-- Ingrédient -->
			<v-col cols="12" md="6">
				<v-combobox
					v-model="newIngredient.name"
					:items="ingredientsList"
					:label="$t('ingredients.label') + ' *'"
					variant="underlined"
					hide-details
					clearable
					:error="v$.name.$error"
					:error-messages="nameErrors"
				/>
			</v-col>

			<!-- Quantité -->
			<v-col cols="6" md="3">
				<v-text-field
					v-model.number="newIngredient.quantity"
					:label="$t('ingredients.quantity.label') + ' *'"
					variant="underlined"
					type="number"
					min="0"
					clearable
					:error="v$.quantity.$error"
					:error-messages="quantityErrors"
				/>
			</v-col>

			<!-- Unité -->
			<v-col cols="6" md="2">
				<v-combobox
					v-model="newIngredient.unityId"
					:items="unitiesUnique"
					item-title="name"
					item-value="id"
					:label="$t('ingredients.unity.label') + ' *'"
					variant="underlined"
					clearable
					:error="v$.unityId.$error"
					:error-messages="unityIdErrors"
				/>
			</v-col>

			<!-- Bouton Ajouter -->
			<v-col cols="12" md="1" class="d-flex align-center justify-end">
				<v-icon
					color="primary"
					class="add-icon"
					:class="{ 'icon-disabled': !canAdd }"
					@click="addIngredient"
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
import { ingredientValidation } from "../utils/validationRules.js";
import useVuelidate from "@vuelidate/core";
import i18n from "@/i18n/i18n.js";

export default {
	name: "ingredients",
	props: {
		ingredients: {
			type: Array,
			default: () => [],
			required: true,
		},
	},
	data() {
		return {
			ingredientsStore: useIngredientsStore(),
			recipesStore: useRecipesStore(),
			newIngredient: {
				name: "",
				quantity: null,
				unityId: null,
			},
			v$: null,
		};
	},
	validations() {
		return ingredientValidation;
	},
	computed: {
		ingredientsList() {
			return this.recipesStore.ingredientList;
		},
		unitiesUnique() {
			return this.ingredientsStore.unitiesList;
		},
		canAdd() {
			return (
				this.newIngredient.name &&
				this.newIngredient.quantity > 0 &&
				this.newIngredient.unityId !== null
			);
		},
		nameErrors() {
			if (!this.v$.name.$error) {
				return [];
			}
			const errors = [];
			const rules = this.v$.name;
			if (rules.required.$invalid)
				errors.push(i18n.global.t("validation.required"));
			if (rules.minLength.$invalid)
				errors.push(i18n.global.t("validation.minLength", { min: 4 }));
			if (rules.maxLength.$invalid)
				errors.push(i18n.global.t("validation.maxLength", { max: 25 }));
			return errors;
		},
		quantityErrors() {
			const errors = [];
			const rules = this.v$.quantity;

			if (rules.$error) {
				if (rules.required.$invalid)
					errors.push(i18n.global.t("validation.required"));
				if (rules.numeric?.$invalid)
					errors.push(i18n.global.t("validation.numeric"));
				if (rules.positive?.$invalid)
					errors.push(i18n.global.t("validation.positiveNumber"));
			}

			return errors;
		},
		unityIdErrors() {
			if (!this.v$.unityId.$error) {
				return [];
			}
			const errors = [];
			const rules = this.v$.unityId;
			if (rules.$error) {
				if (rules.required.$invalid)
					errors.push(i18n.global.t("validation.required"));
			}
			return errors;
		},
	},
	created() {
		this.ingredientsStore.fetchUnities();
		this.v$ = useVuelidate(ingredientValidation, this.newIngredient);
	},
	methods: {
		getUnityName(unityData) {
			if (!unityData) return "?";

			// Si unityData est un objet (ex: {id: 1, name: "g"}), on extrait l'id
			const unityId = typeof unityData === "object" ? unityData.id : unityData;

			const unity = this.unitiesUnique.find((u) => u.id === unityId);
			return unity ? unity.name : "?";
		},
		async addIngredient() {
			await this.v$.$validate();

			if (this.v$.$invalid) return;
			if (!this.canAdd) return;

			// On extrait l'id de l'unité si c'est un objet
			const unityId =
				typeof this.newIngredient.unityId === "object"
					? this.newIngredient.unityId.id
					: this.newIngredient.unityId;

			const newIng = {
				ingredient: { name: this.newIngredient.name },
				quantity: this.newIngredient.quantity,
				unityId,
			};

			const updatedIngredients = [...this.ingredients, newIng];
			this.$emit("update:ingredients", updatedIngredients);

			await this.ingredientsStore.addIngredient(
				this.newIngredient.name,
				this.newIngredient.quantity,
				unityId
			);

			// Réinitialiser le formulaire
			this.newIngredient = { name: "", quantity: null, unityId: null };
		},
		deleteIngredient(index) {
			const updatedIngredients = [...this.ingredients];
			updatedIngredients.splice(index, 1);
			this.$emit("update:ingredients", updatedIngredients);
		},
	},
};
</script>

<style scoped>
.add-icon {
	color: #5d827f !important;
	cursor: pointer;
}
.icon-disabled {
	color: gray !important;
	cursor: not-allowed;
}
.v-chip,
.v-text-field input {
	color: #5d827f;
}
.v-text-field {
	color: #5d827f;
}
</style>
