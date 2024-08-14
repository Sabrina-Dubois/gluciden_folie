<template>
	<div>
		<h1>SE CONNECTER</h1>
		<h2>Nous sommes heureux de vous revoir</h2>
		<v-card class="connection d-flex align-center">
				<v-form @submit.prevent>
					<v-text-field
						v-model="email"
						:rules="rules"
						label="email@email.com"
						hide-details
						variant="solo"
					></v-text-field>
					<v-text-field
						v-model="password"
						:rules="rules"
						label="Saisir votre mot de passe"
						hide-details
						variant="solo"
					></v-text-field>
					<v-btn class="custom-btn" ml-5 rounded="" type="submit"
						>Se connecter</v-btn
					>
					<p>ou</p>
					<v-btn class="custom-btn" ml-5 rounded="" type="submit"
						>Créer un compte</v-btn
					>
				</v-form>
		</v-card>
	</div>
</template>

<script>
export default {
	data: () => ({
		email: "",
		rules: [],
		password: "",
	}),
};
</script>


<style scoped>

.connection {
	max-width: 900px;
	margin: auto;
}
h1,
h2 {
	text-align: center;
	margin: 20px;
}

h2 {
	font-family: "Laila", serif;
	font-weight: 400;
	font-style: normal;
	color: #5d827f;
}

.v-card {
	background-color:  #d3beb1;
	text-align: center; /* Centrer le texte */
	margin: 0 auto; /* Centrer les autres éléments */
	padding: 20px; /* Ajouter un espacement intérieur */
}
.v-form {
	background-color: #d3beb1;
	
}

.v-text-field {
	max-height: 20px;
	max-width: auto;
	margin-bottom: 90px;
}

.v-btn {
	justify-items: center;
	background-color: #5d827f;
	color: #d3beb1;
}
</style>