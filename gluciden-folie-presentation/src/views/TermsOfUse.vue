<template>
	<v-container class="container" fluid>
		<h1>{{ $t("terms_of_use.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto" elevation="4">
			<v-container class="text" fluid>
				<p>{{ $t("terms_of_use.paragraph1") }}</p> <br>
				<p>{{ $t("terms_of_use.paragraph2") }}</p> <br>
				<p>{{ $t("terms_of_use.paragraph3") }}</p> <br>
				<p>
					{{ $t("terms_of_use.paragraph4").split("nous contacter")[0] }}
					<router-link to="/contact">nous contacter</router-link>.
				</p><br>
				<p>{{ $t("terms_of_use.paragraph5") }}</p>
			</v-container>
		</v-card>
	</v-container>
</template>

<script scoped></script>

<style scoped>
.container {
	max-width: 900px;
}

.text {
	color: #5d827f;
	font-size: 18px;
	text-align: center;
}

.container a {
  color: #5d827f; 
  text-decoration: underline; 
  font-style: italic;
  font-size: 18px;
  transition: color 0.3s ease;
  cursor: pointer;
  padding-bottom: 2px;
}

.container a:hover {
  color: #f29eb0; 

}
</style>