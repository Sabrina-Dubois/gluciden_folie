export default [
  {
    path: "/",
    name: "home",
    component: () => import("../views/Home.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../views/Authentification.vue"),
  },
  {
    path: "/authentification/:action",
    name: "authentification",
    component: () => import("../views/Authentification.vue"),
  },
  {
    path: "/liste-recettes",
    name: "recipesList",
    component: () => import("@/views/RecipesList.vue"),
  },
  {
    path: "/recettes/:id",
    name: "recipeDetails",
    component: () => import("@/views/RecipeDetails.vue"),
    props: true,
  },
  {
    path: "/:notFound",
    name: "notfound",
    component: () => import("../views/NotFound.vue"),
  },

  {
    path: "/liste-categories",
    name: "categoriesList",
    component: () => import("../views/CategoriesList.vue"),
  },
  {
    path: "/apropos",
    name: "about",
    component: () => import("../views/About.vue"),
  },
  {
    path: "/politique-confidentialite",
    name: "privacyPolicy",
    component: () => import("../views/PrivacyPolicy.vue"),
  },
  {
    path: "/termes-utilisation",
    name: "termsOfUse",
    component: () => import("../views/TermsOfUse.vue"),
  },
  {
    path: "/plan",
    name: "siteMap",
    component: () => import("../views/SiteMap.vue"),
  },
  {
    path: "/contact",
    name: "contact",
    component: () => import("../views/Contact.vue"),
  },
];
