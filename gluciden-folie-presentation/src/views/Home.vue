<template>
	<CategoriesLayout :showCreateCategory="false" />
	<HomeComp />
</template>

<script>
import HomeComp from "../components/HomeComp.vue";
import CategoriesLayout from "../components/CategoriesLayout.vue";

export default {
	name: "Home",
	components: {
		HomeComp,
		CategoriesLayout,
	},
};
</script>

<style>
</style>