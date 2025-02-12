import { createI18n } from "vue-i18n";
import en from "./i18n/en.json";
import fr from "./i18n/fr.json";

const i18n = createI18n({
  locale: "fr",
  fallbackLocale: "en",
  messages: {
    en,
    fr,
  },
});

// const userLang = navigator.language || navigator.userLanguage;
//     if (!userLang.startsWith('fr')) {
//         i18n.global.locale = 'en' ;
//     }

export default i18n;
