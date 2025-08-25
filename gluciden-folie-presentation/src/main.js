import App from "./App.vue";
import router from "./router";
import "./assets/main.css";
import { createApp } from "vue";
import { createPinia } from "pinia";
import { createVuetify } from "vuetify";
import i18n from "./i18n/i18n";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignicons.css";
import "@mdi/font/css/materialdesignicons.min.css";
import "vuetify/styles";

const vuetify = createVuetify({
  components,
  directives,
});
const app = createApp(App);
const pinia = createPinia();

app.use(router);
app.use(vuetify);
app.use(i18n);
app.use(pinia);

app.mount("#app");
