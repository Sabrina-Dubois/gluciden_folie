<template>
	<v-card flat class="pa-4">
		<h2 class="mb-4">{{ $t("steps.title") }}</h2>

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
					class="required-field"
					:label="$t('steps.label') + ' *'"
					variant="underlined"
					rows="1"
					counter="500"
					@change="emitSteps"
					clearable
				/>
			</v-card-text>
		</v-card>

		<!-- Ajouter une étape -->
		<v-btn class="custom-btn mb-6" prepend-icon="mdi-plus" @click="addStep">
			{{ $t("steps.button") }}
		</v-btn>

		<!-- Zone résumé -->
		<h2>{{ $t("steps.instructions.title") }}</h2>
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
	name: "Steps",
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
	watch: {
		steps: {
			immediate: true,
			handler(newSteps) {
				if (!Array.isArray(newSteps)) return;
				if (JSON.stringify(this.localSteps) !== JSON.stringify(newSteps)) {
					this.localSteps = [...newSteps];
				}
			},
		},
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
				.filter((step) => step.description !== undefined)
				.map((step, index) => ({
					number: index + 1,
					description: step.description?.trim() ?? "",
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
	color: #5d827f;
}

.step-textarea {
	border-radius: 12px;
	border: 2px solid #d3beb1;
	color: #5d827f;
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
