import { createRouter, createWebHistory } from "vue-router";

import adminRoutes from "./adminRoutes";
import userRoutes from "./userRoutes";
import visitorRoutes from "./visitorRoutes";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...visitorRoutes, ...userRoutes, ...adminRoutes],
});

// 🔒 Guard global de sécurité
router.beforeEach((to, from, next) => {
  //to est l’objet qui contient les infos de la page où l’utilisateur veut aller.
  //next() est une fonction que tu dois appeler pour continuer la navigation ou la rediriger
  const token = localStorage.getItem("jwt");

  // Si la route demande un rôle admin
  if (to.meta.requiresAdmin) {
    // Pas connecté → redirection vers login
    if (!token) {
      return next({ name: "login" });
    }
    try {
      // Décodage du token pour extraire le rôle
      const payload = JSON.parse(atob(token.split(".")[1]));

      // Si le rôle est ROLE_ADMIN → OK
      if (payload.role === "ROLE_ADMIN") {
        return next();
      } else {
        // Le user est connecté mais pas admin → redirection refus
        return next({ name: "unauthorized" });
      }
    } catch (error) {
      console.error("Token invalide :", error);
      return next({ name: "login" }); // Token cassé → on renvoie au login
    }
  }
  // Aucune restriction → accès autorisé
  next();
});

export default router;
