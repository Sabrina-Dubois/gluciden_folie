<template>
	<v-container class="container" fluid>
		<h1>{{ $t("site_map.title") }}</h1>
		<v-card class="d-flex mx-auto my-auto" elevation="4" >
			<v-container class="text" fluid>
				<p class="intro">
					Retrouvez ici les pages principales du site pour naviguer facilement.
				</p>
				<ul class="sitemap-list">
					<li>
						<router-link to="/">{{ $t("site_map.home") }}</router-link>
					</li>
					<li>
						<router-link to="/apropos">{{ $t("site_map.about") }}</router-link>
					</li>
					<li>
						<router-link to="/privacyPolicy">{{
							$t("site_map.privacy_policy")
						}}</router-link>
					</li>
					<li>
						<router-link to="/terms">{{
							$t("site_map.terms_of_use")
						}}</router-link>
					</li>
					<li>
						<router-link to="/contact">{{
							$t("site_map.contact")
						}}</router-link>
					</li>
				</ul>
			</v-container>
		</v-card>
	</v-container>
</template>

<script scoped>
export default {};
</script>

<style scoped>
.container {
	max-width: 900px;
}
.text {
	color: #5d827f;
	font-size: 18px;
	text-align: center;
	padding: 1.5rem;
}

.intro {
	margin-bottom: 1rem;
	color: #5d827f;
}

.sitemap-list {
	list-style: none;
	padding: 0;
	margin: 0;
	display: flex;
	flex-direction: column;
	gap: 0.8rem;
	justify-content: center;
}

.sitemap-list a {
	color: #5d827f;
	text-decoration: underline;
	font-style: italic;
	font-size: 18px;
	transition: color 0.3s ease;
	cursor: pointer;
	padding-bottom: 2px;
}

.sitemap-list a:hover {
	color: #f29eb0;
}
</style>
