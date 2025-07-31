<template>
	<v-container class="container" fluid>
		<h1>{{ $t("privacy_policy.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto" elevation="4">
			<v-container class="text" fluid>
				<p>{{ $t("privacy_policy.paragraph1") }}</p> <br>
				<p>{{ $t("privacy_policy.paragraph2") }}</p> <br>
				<p>{{ $t("privacy_policy.paragraph3") }}</p> <br>
				<p>
					<!-- Pour le lien, tu peux mettre un router-link ici -->
					{{ $t("privacy_policy.paragraph4").split("nous contacter")[0] }} 
					<router-link to="/contact">nous contacter</router-link>.
				</p> <br>
				<p>{{ $t("privacy_policy.paragraph5") }}</p>
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