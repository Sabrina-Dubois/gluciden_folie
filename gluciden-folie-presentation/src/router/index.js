import { createRouter, createWebHistory } from "vue-router";

import adminRoutes from "./adminRoutes";
import userRoutes from "./userRoutes";
import visitorRoutes from "./visitorRoutes";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...visitorRoutes, ...userRoutes, ...adminRoutes],
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("jwt");

  if (to.meta.requiresAdmin) {

    if (!token) {
      return next({ name: "login" });
    }
    try {

      const payload = JSON.parse(atob(token.split(".")[1]));
      if (payload.role === "ROLE_ADMIN") {
        return next();
      } else {
        return next({ name: "unauthorized" });
      }
    } catch (error) {
      console.error("Token invalide :", error);
      return next({ name: "login" });
    }
  }
  next();
});

export default router;
