import { required, minLength, maxLength, helpers } from "@vuelidate/validators";
import { messages } from "./validationMessages.js";

// Validation champs obligatoires
export const requiredField = helpers.withMessage(messages.required, required);

// Règles longueurs
export const lengthRules = (min, max) => ({
  minLength: helpers.withMessage(messages.minLength(min), minLength(min)),
  maxLength: helpers.withMessage(messages.maxLength(max), maxLength(max)),
});

// Validation taille image
const validImageSize = helpers.withMessage(
  messages.validImageSize,
  (value) => !value || (value instanceof File && value.size <= 5242880)
); // 5 mo en octets => 5 * 1024 * 1024 = 5242880 octets

// Validation type image
const validImageType = helpers.withMessage(
  messages.validImageType,
  (value) =>
    !value || // Pas d'image = pas d'erreur
    (value instanceof File && // Vérifie le type seulement si c'est un fichier
      (value.type === "image/png" || value.type === "image/jpeg"))
);

// Validation adresse email
const validEmail = helpers.withMessage(messages.validEmail, (value) =>
  /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(value)
);

// Validation mot de passe
const validPassword = helpers.withMessage(
  messages.validPassword,
  (value) => /[A-Z]/.test(value) && /[a-z]/.test(value) && /\d/.test(value) && /[!@#$%^&*(),.?":{}|<>]/.test(value)
);

export const recipeValidation = {
  form: {
    recipeName: {
      required: requiredField,
      ...lengthRules(4, 100),
    },
    recipePicture: {
      required: requiredField,
      validImageType,
      validImageSize,
    },
  },
};
export const categoryValidation = {
  categoryName: {
    required: requiredField,
    ...lengthRules(4, 50),
  },
};

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
