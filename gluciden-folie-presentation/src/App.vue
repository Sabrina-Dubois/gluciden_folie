<template>
	<v-app>
		<Header />
		<main class="main-content">
			<v-container>
				<RouterView />
			</v-container>
		</main>
		<Footer class="temp"/>
	</v-app>
</template>


<script>
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";

export default {
	name: "App",
	components: {
		Header,
		Footer
	},
};
</script>

<style scoped>
html,
Authentification,
body,
#app,
.v-application {
	height: 100%;
	margin: 0;
	padding: 0;
	background-color:  #f5ede8;
}
.temp {
	max-height: 100px;
	position: fixed;
	bottom: 0px;
}
.main-content {
	padding-top: 90px;
	padding-bottom: 50px;
	background-color:  #f5ede8;
}

.v-ripple__container {
	display: none !important;
}
.v-window__controls {
	padding: 0;
}
</style>
