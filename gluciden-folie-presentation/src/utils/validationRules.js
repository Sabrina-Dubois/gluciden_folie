import { required, minLength, maxLength, helpers, numeric } from "@vuelidate/validators";
import i18n from "../i18n/i18n";

const t = i18n.global.t;

export const requiredField = helpers.withMessage(() => i18n.global.t("validation.required"), required);

export const lengthRules = (min, max) => ({
  minLength: helpers.withMessage(() => t("validation.minLength", { min }), minLength(min)),
  maxLength: helpers.withMessage(() => t("validation.maxLength", { max }), maxLength(max)),
});

export const positiveNumber = helpers.withMessage(
  () => t("validation.positiveNumber"),
  (value) => Number(value) > 0
);


const validImageType = helpers.withMessage(
  () => i18n.global.t("validation.validImageType"),
  (value) =>
    !value || typeof value === "string" || (value instanceof File && ["image/jpeg", "image/png"].includes(value.type))
);

const validImageSize = helpers.withMessage(
  () => i18n.global.t("validation.validImageSize"),
  (value) => !value || typeof value === "string" || (value instanceof File && value.size <= 5242880)
);

const requiredIfNoImage = helpers.withMessage(
  () => i18n.global.t("validation.required"),
  (value) => (typeof value === "string" && value.trim() !== "") || value instanceof File
);

const validEmail = helpers.withMessage(
  () => t("validation.validEmail"),
  (value) => /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(value)
);
const validPassword = helpers.withMessage(
  () => t("validation.validPassword"),
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
      required: helpers.withMessage(
        () => t("validation.required"),
        (value) => {
          if (value == null) return false;

          if (typeof value === "number") {
            return value >= 1 && value <= 4;
          } else if (typeof value === "string") {
            const allowedStrings = ["FACILE", "MOYEN", "DIFFICLIE", "EXPERT"];
            return allowedStrings.includes(value);
          } else {
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
