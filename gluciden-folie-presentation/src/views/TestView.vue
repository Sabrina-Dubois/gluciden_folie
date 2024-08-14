<template>
	<div class="test-view">
		<h1>Page de Test</h1>
		<p>
			Vous êtes sur la page de test. Vous pouvez naviguer vers d'autres pages
			via la barre de navigation.
		</p>
		<router-link to="/"><img alt="Vue logo" class="logo" src="@/assets/images/logo.png" />
		</router-link>
	</div>
</template>

<script>
export default {
	name: "TestView",
};
</script>

<style scoped>
.test-view {
	text-align: center;
	margin: 20px;
}

</style>