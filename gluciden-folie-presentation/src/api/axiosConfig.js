import axios from "axios";

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

export default apiClient;
