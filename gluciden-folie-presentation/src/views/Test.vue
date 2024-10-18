<template>
	<div class="test-view">
		<h1>{{ $t('test_page.title') }}</h1>
		<p>
			{{ $t('test_page.text') }}
		</p>
		<router-link to="/"><img alt="Vue logo" class="logo" src="@/assets/images/logo.png" />
		</router-link>
	</div>
</template>

<script>
export default {
	name: "Test",
};
</script>

<style scoped>
.test-view {
	text-align: center;
	margin: 20px;
}

</style>