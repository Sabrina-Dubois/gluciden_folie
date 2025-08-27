import axios from "axios";
import router from "@/router";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  //withCredentials: true,
});

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("jwt");
    if (token) {
      if (!config.headers) config.headers = {};
      if (!config.headers.Authorization) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    if (config.data instanceof FormData) {
      delete config.headers["Content-Type"]; 
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
      localStorage.removeItem("jwt");

      if (!originalRequest._retry && window.location.pathname !== "/login") {
        originalRequest._retry = true;

        await router.push("/login");
      }
    }

    return Promise.reject(error);
  }
);

export default apiClient;
