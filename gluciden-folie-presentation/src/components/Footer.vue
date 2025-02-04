<template>
	<v-footer class="custom-footer">
		<v-container class="footer text-center">
			<div class="footer-row">
				<h4>{{ $t("footer.notices") }}</h4>
			</div>

			<div class="footer-row">
				<li><a href="/about">{{ $t("footer.about") }}</a></li>
				<li><a href="/privacy">{{ $t("footer.confidentiality") }}</a></li>
				<li><a href="/terms">{{ $t("footer.condition") }}</a></li>
				<li><a href="/sitemap">{{ $t("footer.map") }}</a></li>
				<li><a href="/contact">{{ $t("footer.contact") }}</a></li>
			</div>

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
	align-items: center;
	justify-content: center;
	padding: 0 20px;
	min-height: 100px;
	justify-content: space-between;
	width: 100%;
}

.footer {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	gap: 10px;
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
	text-decoration: none;
	color: #5d827f;
	font-size: 15px;
}

.footer-row li a:hover {
	text-decoration: underline;
}

.footer-row h4 {
	margin: 0;
	color: #5d827f;
}

.footer-row p {
	font-size: 15px;
	margin: 0;
	color: #5d827f;
}
</style>