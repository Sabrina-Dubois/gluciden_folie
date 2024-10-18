import { createI18n } from "vue-i18n";
import en from './i18n/en.json';
import fr from './i18n/fr.json';

const i18n = createI18n({
    locale: 'en',
    fallbackLocale: 'en',
    messages: {
        en,
        fr,
    }
});

export default i18n;