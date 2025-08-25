<template>
	<v-footer class="custom-footer">
		<v-container>
			<div class="footer-row">
				<h4>{{ $t("footer.notices") }}</h4>
			</div>

			<ul class="footer-row">
				<li>
					<router-link :to="{ name: 'about' }">{{
						$t("footer.about")
					}}</router-link>
				</li>
				<li>
					<router-link :to="{ name: 'privacyPolicy' }">{{
						$t("footer.privacy_policy")
					}}</router-link>
				</li>
				<li>
					<router-link :to="{ name: 'termsOfUse' }">{{
						$t("footer.terms_of_use")
					}}</router-link>
				</li>
				<li>
					<router-link :to="{ name: 'siteMap' }">{{
						$t("footer.site_map")
					}}</router-link>
				</li>
				<li>
					<router-link :to="{ name: 'contact' }">{{
						$t("footer.contact")
					}}</router-link>
				</li>
			</ul>

			<div class="footer-row">
				<p>&copy; {{ currentYear }} {{ $t("footer.copyright") }}</p>
			</div>
		</v-container>
	</v-footer>
</template>

<script>
export default {
	name: "Footer",
	data() {
		return {
			currentYear: new Date().getFullYear(),
		};
	},
};
</script>

<style>
.custom-footer {
	background-color: #d3beb1 !important;
	display: flex;
	flex-direction: column;
	align-items: center;
	text-align: center;
	padding: 20px;
	width: 100%;
}

.footer-row {
	text-align: center;
}

.footer-row li {
	display: inline-block;
	margin: 0 10px;
	list-style: none;
}

.footer-row li a {
	color: #5d827f;
	font-size: 15px;
}

.footer-row li a:hover {
	text-decoration: underline;
}

.footer-row h4,
.footer-row p {
	font-size: 15px;
	margin: 0;
	color: #5d827f;
}
</style>