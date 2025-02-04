import axios from "axios";
import router from "@/router";

const apiClient = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

// Intercepeteur -> ajoute Token auto
// Instance qui définit intercepeteur avant envoie de la requete
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("jwt"); // Recup token local storage
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config; // renvoie la config modifiée
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Intercepteur -> erreur 401
apiClient.interceptors.response.use(
  (response) => response, // On renvoie la réponse
  async (error) => {
    if (error.response && error.response.status === 401) {
      // Si le jeton est expiré ou invalide > supprime le token
      localStorage.removeItem("jwt");
      // Rediriger l'utilisateur vers la page de connexion
      router.push("/login");
    }
    return Promise.reject(error);
  }
);


export default apiClient;
