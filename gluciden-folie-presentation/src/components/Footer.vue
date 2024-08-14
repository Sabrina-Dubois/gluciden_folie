<template>
 <v-footer>
  <v-container class="footer text-center">
      <p>&copy; {{ currentYear }} GLUCID'EN FOLIE. Tous droits rèservès.</p>
    </v-container>
  </v-footer>

</template>

<script>
export default {
  name: 'Footer',
  data() {
    return {
      currentYear: new Date().getFullYear()
    };
  }
}
</script>

<style>
.v-footer {
  background-color: #D3BEB1;
  color: #5D827F;
  width: 100%;
  height: 40px;
}

.footer {
  display: flex;
  align-items: center;
  justify-content: center;
  max-height: 200px;
}

</style>