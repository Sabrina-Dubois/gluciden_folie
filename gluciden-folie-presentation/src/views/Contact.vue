<template>
	<v-container>
		<div class="main-content custom-bg">
			<h1>{{ $t("contact.title") }}</h1>
			<v-card class="contactForm" elevation="4" max-width="800px">
				<!-- Formulaire de contact -->
				<v-form>
					<v-text-field :label="$t('contact.name')" outlined></v-text-field>
					<v-text-field :label="$t('contact.mail')" outlined></v-text-field>
					<v-textarea :label="$t('contact.message')" outlined></v-textarea>
					<v-btn class="custom-btn" ml-5 rounded="" type="submit">
						{{ $t("contact.button") }}
					</v-btn>
				</v-form>
			</v-card>
		</div>
	</v-container>
</template>

<style scoped>
/* --- Container --- */
.main-content.custom-bg {
	padding: 20px;
	display: flex;
	flex-direction: column;
	align-items: center;
	padding-top: 10px;
}

.v-form {
	background-color: white;
	width: 100%;
}

.v-text-field,
.v-file-input {
	max-width: auto;
	margin-bottom: 30px;
	padding-left: 20px;
	color: #5d827f;
}

/* *** Boutons *** */
.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}

/* --- Titre --- */
.contact-title {
	text-align: center;
	color: #5d827f;
	margin-bottom: 20px;
}

/* --- Card --- */
.contactForm {
	max-width: 800px;
	width: 100%;
	margin: auto;
	padding: 20px;
}

/* --- Formulaire --- */
.form-content {
	display: flex;
	flex-direction: column;
	gap: 20px;
}

/* --- Bouton --- */
.button-container {
	display: flex;
	justify-content: center;
	margin-top: 10px;
}

.custom-btn {
	background-color: #5d827f;
	color: #d3beb1;
	padding: 10px 30px;
}
</style>