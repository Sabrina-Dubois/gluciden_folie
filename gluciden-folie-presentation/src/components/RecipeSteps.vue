<template>
	<v-card flat class="pa-4">
		<h3 class="mb-4">{{ $t("steps.title") }}</h3>

		<!-- Étapes une par une -->
		<v-card
			v-for="(step, index) in localSteps"
			:key="index"
			class="mb-4 step-card"
		>
			<v-card-title class="d-flex justify-space-between align-center">
				<span class="step">{{ $t("steps.card-title") }} {{ index + 1 }}</span>
			</v-card-title>

			<v-card-text>
				<v-textarea
					v-model="localSteps[index].description"
					:label="$t('steps.label') + ' *'"
					variant="underlined"
					rows="1"
					counter="500"
					@input="emitSteps"
					clearable
				/>
			</v-card-text>
		</v-card>

		<!-- Ajouter une étape -->
		<v-btn class="custom-btn mb-6" prepend-icon="mdi-plus" @click="addStep">
			{{ $t("steps.button") }}
		</v-btn>

		<!-- Zone résumé -->
		<h3>{{ $t("steps.instructions.title") }}</h3>
		<v-textarea
			:value="allStepsText"
			:label="$t('steps.instructions.label') + ' *'"
			class="step-textarea"
			rows="5"
			readonly
			variant="underlined"
		/>
	</v-card>
</template>

<script>

export default {
	name: "RecipeSteps",
	props: {
		steps: {
			type: Array,
			required: true,
		},
	},
	emits: ["update:steps"],
	data() {
		return {
			localSteps: [],
		};
	},
	mounted() {
		// Initialisation une seule fois
		this.localSteps = [...this.steps];
	},
	computed: {
		allStepsText() {
			return this.localSteps
				.map((step, idx) => `${idx + 1}. ${step.description.trim()}`)
				.filter((line) => line.trim() !== `${line.split(". ")[0]}.`)
				.join("\n");
		},
	},
	methods: {
		addStep() {
			this.localSteps.push({ description: "" });
			this.emitSteps();
		},
		removeStep(index) {
			this.localSteps.splice(index, 1);
			this.emitSteps();
		},
		emitSteps() {
			const stepsWithNumbers = this.localSteps
				.filter((step) => step.description?.trim()) // ignore les vides
				.map((step, index) => ({
					number: index + 1,
					description: step.description.trim(),
				}));

			this.$emit("update:steps", stepsWithNumbers);
		},
	},
};
</script>

<style scoped>
.step-card {
	border-radius: 12px;
	border: 2px solid #d3beb1;
	color : #5d827f;
}

.step-textarea{
	border-radius: 12px;
	border: 2px solid #d3beb1;
	color : #5d827f;
}

.step-content {
	text-align: left;
}



.step {
	color: #5d827f;
}

.custom-btn {
	background-color: #5d827f;
	color: #d3beb1;
}
</style>
