import { required, minLength, maxLength, helpers, numeric } from "@vuelidate/validators";
import { messages } from "./validationMessages.js";

// Validation champs obligatoires
export const requiredField = helpers.withMessage(messages.required, required);

// Règles longueurs
export const lengthRules = (min, max) => ({
  minLength: helpers.withMessage(messages.minLength(min), minLength(min)),
  maxLength: helpers.withMessage(messages.maxLength(max), maxLength(max)),
});

// Quantités
export const positiveNumber = helpers.withMessage(
  messages.positiveNumber,
  (value) => Number(value) > 0
)

// ✅ Accepte les fichiers OU les strings existants (nom de l'image existante)
const validImageType = helpers.withMessage(
  messages.validImageType,
  (value) =>
    !value ||
    typeof value === "string" || // image existante déjà en BDD
    (value instanceof File && (value.type === "image/jpeg" || value.type === "image/png"))
);

// ✅ Pareil ici, accepte aussi les strings (pas de size à valider pour elles)
const validImageSize = helpers.withMessage(
  messages.validImageSize,
  (value) =>
    !value ||
    typeof value === "string" || // on ne vérifie pas la taille si image déjà présente
    (value instanceof File && value.size <= 5242880)
);

// ✅ Le champ est requis si ni fichier ni nom de fichier
const requiredIfNoImage = helpers.withMessage(messages.required, (value) => {
  if (typeof value === "string" && value.trim() !== "") return true;
  if (value instanceof File) return true;
  return false;
});

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
      required: requiredField,
    },
  },
};
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
