<template>
	<div class="not-found">
		<h1>{{ $t("not_found.title") }}</h1>
		<p>{{ $t("not_found.message") }}</p>
		<router-link to="/" class="home-link">{{
			$t("not_found.button")
		}}</router-link>
	</div>
</template>

<script>
export default {
	name: "NotFoundPage",
};
</script>

<style scoped>
.not-found {
	text-align: center;
	color: #5d827f;
	margin-top: 4rem;
}

.not-found h1 {
	font-size: 2rem;
	margin-bottom: 1rem;
}

.not-found p {
	margin-bottom: 1.5rem;
}

.home-link {
	color: white;
	background: #5d827f;
	padding: 0.5rem 1rem;
	border-radius: 8px;
	text-decoration: none;
}

.home-link:hover {
	background: #476a67;
}
</style>
