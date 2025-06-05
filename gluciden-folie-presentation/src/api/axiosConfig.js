import axios from "axios";
import router from "@/router";

const apiClient = axios.create({
  baseURL: "http://localhost:8080",
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
    return Promise.reject(error); // Rejeter en cas d'erreur
  }
);

// Intercepteur -> Gestion de l'erreur 401
apiClient.interceptors.response.use(
  (response) => response, // Si la réponse est valide, on la renvoie
  async (error) => {
    if (error.response && error.response.status === 401) {
      console.log("Erreur 401 : Jeton invalide ou expiré.");
      // Supprimer le jeton si il est invalide ou expiré
      localStorage.removeItem("jwt");
      // Rediriger l'utilisateur vers la page de connexion
      if (window.location.pathname !== "/login") {
        await router.push("/login"); // Assure-toi que cela redirige correctement
      }
    }
    return Promise.reject(error); // Rejeter l'erreur pour que l'appel d'API puisse la gérer
  }
);

export default apiClient;
