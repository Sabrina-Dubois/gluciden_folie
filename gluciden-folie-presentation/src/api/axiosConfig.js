import axios from "axios";
import router from "@/router";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  //withCredentials: true,
});

// Intercepteur -> ajoute Token automatique
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("jwt");
    if (token) {
      if (!config.headers) config.headers = {};
      if (!config.headers.Authorization) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    // Si on envoie du FormData, laisser Axios définir le Content-Type
    if (config.data instanceof FormData) {
      delete config.headers["Content-Type"]; // Axios le gère automatiquement
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Intercepteur -> Gestion de l'erreur 401
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response && error.response.status === 401) {
      // Supprimer le jeton
      localStorage.removeItem("jwt");

      // Éviter la boucle infinie en vérifiant un flag custom "_retry"
      if (!originalRequest._retry && window.location.pathname !== "/login") {
        originalRequest._retry = true; // Marque la requête comme "déjà gérée"

        // Redirige vers /login
        await router.push("/login");
      }
    }

    return Promise.reject(error);
  }
);

export default apiClient;
