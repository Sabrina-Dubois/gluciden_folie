<template>
	<v-container class="container" fluid>
		<h1>{{ $t("about.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto" elevation="4">
			<v-container class="text" fluid>
				<p>{{ $t("about.paragraph1") }}</p>
				<br />
				<p>{{ $t("about.paragraph2") }}</p>
				<br />
				<p>{{ $t("about.paragraph3") }}</p>
				<br />
				<p>{{ $t("about.paragraph4") }}</p>
				<br />
				<p>{{ $t("about.paragraph5") }}</p>
			</v-container>
		</v-card>
	</v-container>
</template>

<script scoped>
export default {
	name: "About",
};
</script>

<style scoped>
.container {
	max-width: 900px;
}
.text {
	color: #5d827f;
	font-size: 18px;
	text-align: center;
}
</style>
