import { required, minLength, maxLength, helpers, numeric } from "@vuelidate/validators";
import i18n from "../i18n/i18n";

const t = i18n.global.t;

// Champs obligatoires
export const requiredField = helpers.withMessage(() => i18n.global.t("validation.required"), required);

// **** Règles longueurs ****
export const lengthRules = (min, max) => ({
  minLength: helpers.withMessage(() => t("validation.minLength", { min }), minLength(min)),
  maxLength: helpers.withMessage(() => t("validation.maxLength", { max }), maxLength(max)),
});

// Quantités
export const positiveNumber = helpers.withMessage(
  () => t("validation.positiveNumber"),
  (value) => Number(value) > 0
);

// ✅ fichiers OU les strings existants (nom de l'image existante)
const validImageType = helpers.withMessage(
  () => i18n.global.t("validation.validImageType"),
  (value) =>
    !value || typeof value === "string" || (value instanceof File && ["image/jpeg", "image/png"].includes(value.type))
);

// ✅ strings (pas de size à valider pour elles)
const validImageSize = helpers.withMessage(
  () => i18n.global.t("validation.validImageSize"),
  (value) => !value || typeof value === "string" || (value instanceof File && value.size <= 5242880)
);

// ✅ Le champ est requis si ni fichier ni nom de fichier
const requiredIfNoImage = helpers.withMessage(
  () => i18n.global.t("validation.required"),
  (value) => (typeof value === "string" && value.trim() !== "") || value instanceof File
);

// **** Email et mot de passe ****
const validEmail = helpers.withMessage(
  () => t("validation.validEmail"),
  (value) => /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(value)
);
const validPassword = helpers.withMessage(
  () => t("validation.validPassword"),
  (value) => /[A-Z]/.test(value) && /[a-z]/.test(value) && /\d/.test(value) && /[!@#$%^&*(),.?":{}|<>]/.test(value)
);

// **** Recette ****
export const recipeValidation = {
  form: {
    name: {
      required: requiredField,
      ...lengthRules(4, 100),
    },
    picture: {
      required: requiredIfNoImage,
      validImageType,
      validImageSize,
    },
    difficulty: {
      required: helpers.withMessage(
        () => t("validation.required"),
        (value) => {
          if (value == null) return false;

          if (typeof value === "number") {
            // validation pour les chiffres (create)
            return value >= 1 && value <= 4;
          } else if (typeof value === "string") {
            const allowedStrings = ["FACILE", "MOYEN", "DIFFICLIE", "EXPERT"];
            return allowedStrings.includes(value);
          } else {
            // si ce n'est ni string ni number, invalide
            return false;
          }
        }
      ),
    },

    steps: {
      required: requiredField,
    },
  },
};

// **** Ingrédients ****
export const ingredientValidation = {
  name: {
    required: requiredField,
    ...lengthRules(3, 25),
  },
  quantity: {
    required: requiredField,
    numeric,
    positiveNumber,
  },
  unityId: {
    required: requiredField,
  },
};

// **** Catégorie ****
export const categoryValidation = {
  categoryName: {
    required: requiredField,
    ...lengthRules(4, 50),
  },
};

// **** Compte ****
export const accountValidation = {
  username: {
    required: requiredField,
    validEmail,
    ...lengthRules(4, 50),
  },
  password: {
    required: requiredField,
    validPassword,
    ...lengthRules(8, 72),
  },
};
